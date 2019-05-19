package com.objectcomputing.devo.mqtt.publisher.service;

import com.objectcomputing.devo.mqtt.publisher.model.ApplicationConfig;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import org.apache.log4j.Logger;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Controller("/publish")
public class PublisherController {

    private static Logger logger = Logger.getLogger(PublisherController.class);
    ApplicationConfig config = new ApplicationConfig();
    private MqttClient myMqttClient;

    PublisherController() {
        try {
            createClient();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    private void createClient() throws MqttException {
    // setup MqttConnectOptions - Holds the set of options that control how the Client connects to a server.
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setAutomaticReconnect(true);
        connOpts.setKeepAliveInterval(60);

    // setup MqttClient - Lightweight client for talking to an MQTT server
        System.out.println("Connecting to broker: " + config.getBrokerHostName());
        myMqttClient = new MqttClient(config.getBrokerHostName(), config.getClientId(), new MemoryPersistence());
        myMqttClient.setTimeToWait(2000);
        myMqttClient.connect(connOpts);
        System.out.println("Connected to broker");
    }

    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    public void index() throws RuntimeException {

        try {
            // call Mqtt publish method
            System.out.println("Publishing message: " + config.getContent());
            MqttMessage message = new MqttMessage(config.getContent().getBytes());
            message.setQos(config.getQos());
            message.setRetained(true);
            this.egress(myMqttClient, message);
        } catch (Exception e) {
            logger.error("Error occurred while publishing message: " + e.getMessage());
        }
    }

    public void egress(MqttClient myMqttClient, MqttMessage message) {

        try {
            int x=0;
            while (true) {
                Thread.sleep(1000);

                if (!myMqttClient.isConnected()) {
                    myMqttClient.reconnect();
                }

                myMqttClient.publish(config.getWriteTopic(), message);
                System.out.println("Message published " + x);
                x++;
            }
        } catch (Exception e) {
            logger.error("Disconnected from Broker " + e.getMessage());
        }
    }
}