package com.objectcomputing.devo.mqtt.publisher.model;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

public class ApplicationConfig {

  public String brokerHostName = "tcp:/", writeTopic = null, content = null, interval, noOfMessages, max, min;
  public int qos;
  public String clientId = "Devo-MQTT-Publisher", function, type;

  public ApplicationConfig() {
    try {
      //get values from configuration file
      configValues("get");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void configValues(String val) throws Exception {

    InputStream inputStream = null;
    try {
      Properties config = new Properties();
      String configFileName = "config.properties";

      inputStream = getClass().getClassLoader().getResourceAsStream(configFileName);

      if (inputStream == null) {
        throw new FileNotFoundException("Configuration file " + configFileName + "not found in classpath");
      }
      config.load(inputStream);

      if (val != "get")
        setValues(config);
      else
        getValues(config);

    } catch (Exception e) {
      System.out.println("Exception: " + e);
    } finally {
      if (inputStream != null)
        inputStream.close();
    }
  }

  private void getValues(Properties config) {

    InetAddress ipAddress = null;
    String brokerHostPort;

    try {
      ipAddress = InetAddress.getByName(config.getProperty("hostname"));
    } catch (java.net.UnknownHostException e) {
      System.out.println("Exception: " + e);
    }

    brokerHostPort = config.getProperty("hostport");
    writeTopic = config.getProperty("topic");
    qos = Integer.parseInt(config.getProperty("QOS"));
    content = config.getProperty("content");
    brokerHostName = new StringBuilder().append(brokerHostName).append(ipAddress.toString()).append(brokerHostPort).toString();
  }

  private void setValues(Properties config) {

    config.setProperty("interval", interval);
    config.setProperty("noOfMessages", noOfMessages);
    config.setProperty("function", function);
    config.setProperty("max", max);
    config.setProperty("min", min);
    config.setProperty("type", type);
  }

  public String getBrokerHostName() {
    return brokerHostName;
  }

  public String getWriteTopic() {
    return writeTopic;
  }

  public int getQos() {
    return qos;
  }

  public String getContent() {
    return content;
  }

  public String getInterval() {
    return interval;
  }

  public String getNoOfMessages() {
    return noOfMessages;
  }

  public String getMax() {
    return max;
  }

  public String getMin() {
    return min;
  }

  public String getClientId() {
    return clientId;
  }

  public String getFunction() {
    return function;
  }

  public String getType() {
    return type;
  }
}

