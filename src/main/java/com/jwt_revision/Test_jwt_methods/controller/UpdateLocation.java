package com.jwt_revision.Test_jwt_methods.controller;

import com.jwt_revision.Test_jwt_methods.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/location")
public class UpdateLocation {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping("/update")
    public ResponseEntity<?> updateUserLocation(){

        this.kafkaService.updateLocation("( "+ Math.floor(Math.random()*100) +", "+ Math.floor(Math.random()*100) +" )");

        return new ResponseEntity<>(Map.of("message","location updated"), HttpStatus.OK);

    }
}
