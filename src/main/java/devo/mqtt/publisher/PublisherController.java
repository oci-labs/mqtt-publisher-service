package devo.mqtt.publisher;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

@Controller("/publish")
public class PublisherController {
    private String brokerHostName = "tcp:/";
    private String writeTopic = null;
    private String content = null;
    private int qos;
    String clientId = "Devo-MQTT-Publisher";
    private MqttClient myMqttClient;

    PublisherController() {
        try {
            //set values from configuration file
            System.out.println("inside constructor try");
            setConfig();
            createClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setConfig() throws Exception {

        InetAddress ipAddress;
        String brokerHostPort;
        InputStream inputStream = null;

        try {
            System.out.println("inside config try");
            Properties config = new Properties();
            String configFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);

            if (inputStream != null) {
                config.load(inputStream);
            } else {
                throw new FileNotFoundException("Configuration file " + configFileName + "not found in classpath");
            }

            ipAddress = InetAddress.getByName(config.getProperty("hostname"));
            brokerHostPort = config.getProperty("hostport");
            writeTopic = config.getProperty("topic");
            qos = Integer.parseInt(config.getProperty("QOS"));
            content = config.getProperty("content");
            brokerHostName = new StringBuilder().append(brokerHostName).append(ipAddress.toString()).append(brokerHostPort).toString();

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if(inputStream != null)
                inputStream.close();
        }
        return;
    }

    private void createClient() throws MqttException {
        System.out.println("inside index try");
        // setup MqttConnectOptions - Holds the set of options that control how the Client connects to a server.
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setAutomaticReconnect(true);
        connOpts.setKeepAliveInterval(60);

        // setup MqttClient - Lightweight client for talking to an MQTT server
        System.out.println("Connecting to broker: " + brokerHostName);
        myMqttClient = new MqttClient(brokerHostName, clientId, new MemoryPersistence());
        myMqttClient.setTimeToWait(2000);
        myMqttClient.connect(connOpts);
        System.out.println("Connected to broker");
    }

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    public void index() throws RuntimeException {

        try {
            // call Mqtt publish method
            System.out.println("Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            message.setRetained(true);
            this.egress(myMqttClient, message);

        } catch (Exception e) {
            System.out.println("inside index catch");
            e.printStackTrace();
        }

        return;
    }

    public void egress(MqttClient myMqttClient, MqttMessage message) {

        try {
            System.out.println("inside egress try");
            int x = 0;

            while (true) {
                Thread.sleep(1000);

//                if (!myMqttClient.isConnected()) {
        //            System.out.println("I was here and then i failed.");
                    myMqttClient.reconnect();
      //          }
                myMqttClient.publish(writeTopic, message);
                x++;
                System.out.println("Message published with message" + message + "value of x =" + x);
            }
        } catch (Exception e) {
            System.out.println("Disconnected \n Exception: " + e);
        }
    }
}