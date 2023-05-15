package com.memorynotfound.kafka.controller;

import com.memorynotfound.kafka.model.User;
import com.memorynotfound.kafka.producer.Sender;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("v1")
public class KafkaController {

    @Autowired
    private Sender sender;

    @GetMapping("/publish/{name}")
    public String postMessage(@PathVariable("name") final String name){

        User data = new User();
        data.setAge(15);
        data.setName(name);
        data.setLocation("Texas");

        sender.sendFoo(data);
        sender.sendBar(data);


        return "Message Published Successfully ok";
    }
}
