package com.kevinAri.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinAri.example.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class TestSwaggerController {
    @Autowired
    AppService appService;
    @Autowired
    ObjectMapper objectMapper;




    //	@RequestMapping({"/test-get2"}) // kalau pakai RequestMapping, di swagger jadi berantakan (ada POST, GET, dll)
    @GetMapping(value = "/test-get2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object testGetWithoutBody() {
        try {
            System.out.println("jalan get");
			return "berhasil get2";
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Service failed");
        }
    }
    @GetMapping(value = "/test-get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object testGetWithBody(@RequestBody HashMap<String, Object> body) {
        System.out.println("jalan get");
        try {
            if (body != null) {
                System.out.println("body tidak null");
                return body;
            } else {
                System.out.println("body null");
                return ResponseEntity.ok()
                        .body("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("bad req");
        }
    }
    @DeleteMapping(value = "/test-delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object testDeleteWithBody(@RequestBody HashMap<String, Object> body) {
        try {
            if (body != null) {
                System.out.println("body tidak null");
                return body;
            } else {
                System.out.println("body null");
                return ResponseEntity.ok()
                        .body("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("bad req");
        }
    }
    @PostMapping(value = "/test-post", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object testPostWithBody(@RequestBody HashMap<String, Object> body) {
        try {
            if (body != null) {
                System.out.println("body tidak null");
                return body;
            } else {
                System.out.println("body null");
                return ResponseEntity.ok()
                        .body("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("bad req");
        }
    }
}
