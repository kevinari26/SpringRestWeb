package com.kevinAri.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinAri.example.model.TestDefaultParserRequest;
import com.kevinAri.example.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@RestController
@CrossOrigin
public class RestWebController {
    @Autowired
    AppService appService;
    @Autowired
    ObjectMapper objectMapper;


    @GetMapping(value = "/test")
    public Object test(@RequestBody JsonNode body) {
        try {
            return appService.test(body);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Service failed");
        }
    }


    // testing pakai headers, PathVariable, RequestParam, RequestBody sekaligus
    @PostMapping(value = "/test-all-params-type/{id}")
    public String hello2(@RequestHeader Map<String, String> headers, @PathVariable("id") Long id, @RequestParam String in1, @RequestBody JsonNode body) {
        try {
            return String.format("Output: %d, %s, %s", id, in1, objectMapper.writeValueAsString(body));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping(value = "/testHttpServlet", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object testHttpServlet(HttpServletRequest request) {
        return appService.testHttpServlet(request);
    }

    @PostMapping(value = "/testfile", consumes = {MediaType.ALL_VALUE})
    public Object testFile(@RequestParam MultipartFile file, @RequestParam String param1) throws IOException {
        System.out.println(Arrays.toString(file.getBytes()));
        System.out.println(MediaType.MULTIPART_FORM_DATA_VALUE);
        return new ResponseEntity<>(
                "berhasil",
                HttpStatus.OK);
    }

    @PostMapping(value = "/testDefaultParser", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Object testDefaultParser(@RequestBody TestDefaultParserRequest request) throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(request));
        System.out.println(request.getJavaDate());
        System.out.println(request.getJavaDate().toString());
        System.out.println(request.getJavaDate2());
        System.out.println(request.getJavaDate2().toString());
        return new ResponseEntity<>(
                "berhasil",
                HttpStatus.OK);
    }
}
