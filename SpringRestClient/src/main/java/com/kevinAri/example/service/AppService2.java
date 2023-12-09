package com.kevinAri.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppService2 {
    @Autowired
    ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public void execute() {
        try {
//            log.info("app service 2");
        } catch (Exception e) {
            try {
                e.printStackTrace();
                System.out.println("error");
            } catch (Exception ex) {

            }
        }
    }

}
