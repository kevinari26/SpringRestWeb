package com.kevinAri.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinAri.example.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class ConsumeTestController {
    @Autowired
    AppService appService;
    @Autowired
    ObjectMapper objectMapper;




    @GetMapping(value = "/consume-get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object consumeGet(@RequestBody JsonNode body) {
        try {
            return ResponseEntity.ok()
                    .body(body);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Service failed");
        }
    }

    @PostMapping(value = "/consume-post", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object consumePost(@RequestBody JsonNode body) {
        try {
            return ResponseEntity.ok()
                    .body(body);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Service failed");
        }
    }
}
