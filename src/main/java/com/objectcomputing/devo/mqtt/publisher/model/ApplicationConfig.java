package com.objectcomputing.devo.mqtt.publisher.model;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

public class ApplicationConfig {

    private String brokerHostName;
    private String brokerHostPort;
    private String writeTopic = null;
    private String content = null;
    private String interval;
    private String noOfMessages;
    private String max;
    private String min;
    private String clientId;
    private String function;
    private String type;
    private int qos;

    public ApplicationConfig() {
        this.setBrokerHostPort("1883");
        this.setBrokerHostName("127.0.0.1");
        this.setQos(2);
        this.setWriteTopic("MQTT Examples");
        this.setContent("Message from MqttPublishSample");
        this.setClientId("Devo-MQTT-Publisher");
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

    public String getBrokerHostPort() {
        return brokerHostPort;
    }

    public void setBrokerHostPort(String brokerHostPort) {
        this.brokerHostPort = brokerHostPort;
    }

    public void setBrokerHostName(String hostName) {
        InetAddress ipAddress = null;
        try {
            ipAddress = InetAddress.getByName(hostName);
        } catch (java.net.UnknownHostException e) {
            System.out.println("Exception: " + e);
        }
        this.brokerHostName = new StringBuilder()
              .append("tcp:/")
              .append(ipAddress.toString())
              .append(":")
              .append(this.getBrokerHostPort()).toString();
    }

    public void setWriteTopic(String writeTopic) {
        this.writeTopic = writeTopic;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public void setNoOfMessages(String noOfMessages) {
        this.noOfMessages = noOfMessages;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }
}
