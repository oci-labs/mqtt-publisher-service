package com.objectcomputing.devo.mqtt.publisher.service;

import io.micronaut.runtime.Micronaut;
import org.apache.log4j.BasicConfigurator;

public class Application {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        Micronaut.run(Application.class);
    }
}