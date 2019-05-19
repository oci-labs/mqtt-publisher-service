package com.objectcomputing.devo.mqtt.publisher.service;

import com.objectcomputing.devo.mqtt.publisher.model.ApplicationConfig;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpParameters;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import org.apache.log4j.Logger;

@Controller("/api")
public class MessageController {
    private static Logger logger = Logger.getLogger(MessageController.class);
    ApplicationConfig config = new ApplicationConfig();

    @Post("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public HttpResponse<?> getResponse(HttpHeaders header, HttpParameters parameters) {
        try{
            ApplicationConfig config = new ApplicationConfig();
            //set values requested by broker
            config.setInterval(parameters.get("interval"));
            config.setNoOfMessages(parameters.get("noOfMessages"));
            config.setFunction(parameters.get("function"));
            config.setMax(parameters.get("max"));
            config.setMin(parameters.get("min"));
            config.setType(parameters.get("type"));
        } catch (Exception e){
            logger.error("Error occurred while getting request: " + e.getMessage());
            return HttpResponse.badRequest();
        }

        return HttpResponse.ok();
    }
}

//          http://localhost:3001/api/post?interval=1000&noOfMessages=100&function=linear&max=100&min=1&type=value
//{
//        "interval": "1000",
//        "noOfMessages": "100",
//        "function": "linear",
//        "max": "100",
//        "min": "1",
//        "type": "valve"
//}
