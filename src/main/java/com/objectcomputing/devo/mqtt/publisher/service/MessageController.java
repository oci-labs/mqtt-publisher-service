package com.objectcomputing.devo.mqtt.publisher.service;


import com.objectcomputing.devo.mqtt.publisher.model.ApplicationConfig;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpParameters;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/api")
public class MessageController {

  ApplicationConfig config = new ApplicationConfig();

  @Post("/post")
  @Consumes(MediaType.APPLICATION_JSON)
  public HttpResponse<?> getResponse(HttpHeaders header, HttpParameters parameters) {

    try{
      //set values requested by broker

      config.interval = parameters.get("interval");
      config.noOfMessages = parameters.get("noOfMessages");
      config.function = parameters.get("function");
      config.max = parameters.get("max");
      config.min = parameters.get("min");
      config.type = parameters.get("type");
      config.configValues("Set Values");

      return HttpResponse.ok();

    } catch (Exception e){
      System.out.println("Exception" + e);
    }
    return HttpResponse.badRequest();
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
