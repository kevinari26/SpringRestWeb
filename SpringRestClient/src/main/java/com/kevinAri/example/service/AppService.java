package com.kevinAri.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Service
public class AppService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RestTemplate restTemplate;


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void execute() {
        try {
//            hitRest("1");
//            hitRest("2");
//            hitRest("3");
            hitRest("4");
//            hitRestWithRawHttp();
//            hitRestWithRawHttp2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void hitRest(String option) {
        try {
            String url;
            RequestEntity<Object> requestEntity;
            if ("1".equals(option)) {
                url = "http://localhost:8082/consume-post";
                // body
                String jsonBodyStr = "{\n" +
                        "\t\"asd\":\"sad1\",\n" +
                        "\t\"key2\":\"val2\"\n" +
                        "}";
                // headers
                HttpHeaders headers = new HttpHeaders();
                String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2Njc0NzI4NzQsImlhdCI6MTY2NzQ3Mjc1NH0.-NMVbOnWLWU8_1QKuxZmhJk_SKAd5wl9smzn9k5wf_gD-qTK_4ZQikYFGsgSivY5DojfzxlY10UgFj3Zl2fFMA";
//                headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString("user:pass".getBytes()));
//                headers.add("Authorization", "Bearer " + token);
                // construct entity
                requestEntity = RequestEntity
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers)
                        .body(objectMapper.convertValue(jsonBodyStr, JsonNode.class))
                        ;
                // hit rest
                ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(requestEntity, JsonNode.class);
                System.out.println(responseEntity);
                System.out.println(responseEntity.getBody());
            }
            else if ("2".equals(option)) {
                url = UriComponentsBuilder.fromHttpUrl("http://localhost:8082/test-all-params-type/10")
                        .queryParam("in1", "{in1}")
                        .encode()
                        .toUriString();
                // body
                String jsonBodyStr = "{\n" +
                        "\t\"asd\":\"sad1\",\n" +
                        "\t\"key2\":\"val2\"\n" +
                        "}";
                // params
                Map<String, Object> params = new HashMap<>();
                params.put("in1", "input1");
                // construct entity
                requestEntity = RequestEntity
                        .post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.convertValue(jsonBodyStr, JsonNode.class))
                ;
                // hit rest
                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        requestEntity,
                        String.class,
                        params);
                System.out.println(responseEntity);
                System.out.println(responseEntity.getBody());
            }
            // consume form-data
            else if ("3".equals(option)) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                headers.set("Authorization", "Basic dXNlcjpwYXNz");
                headers.set("RqUID", "1d503047-4a15-41d3-a804-3fcdd7163554");


                Path imagePath = Paths.get("C:\\Users\\arisa\\Downloads", "WhatsApp Image 2024-02-02 at 18.21.05.jpeg");
                byte[] fileByte = Files.readAllBytes(imagePath);
                ByteArrayResource resource = new ByteArrayResource(fileByte) {
                    @Override
                    public String getFilename() {
                        return imagePath.toFile().getName();
                    }
                };

                MultiValueMap<String, Object> inputMap = new LinkedMultiValueMap<>();
                inputMap.add("file", resource);
                inputMap.add("param1", "resource");

                HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(inputMap, headers);

                String url1 = "http://localhost:8082/testfile";
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(url1,
                        entity,
                        String.class);
                System.out.println(responseEntity.getBody());
            }
            // test qmatic
            else if ("4".equals(option)) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                String username = "040b5255-79a3-43dd-8cfe-4a80763519d8";
                String password = "e6f78f42-05e5-4bc0-be10-8c4d9f642972";
                headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString((username+":"+password).getBytes()));
                headers.set("RqUID", "1d503047-4a15-41d3-a804-3fcdd7163554");


                Path imagePath = Paths.get("C:\\Users\\arisa\\Downloads", "WhatsApp Image 2024-02-02 at 18.21.05.jpeg");
                byte[] fileByte = Files.readAllBytes(imagePath);
                ByteArrayResource resource = new ByteArrayResource(fileByte) {
                    @Override
                    public String getFilename() {
                        return imagePath.toFile().getName();
                    }
                };

                MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
                requestBody.add("ocrImage", resource);

                HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(requestBody, headers);

                String url2 = "http://apimanagertzuat.permatabank.com:9320/apiservice/advanceai/face-recognition/v3/ocr-ktp-check";
                ResponseEntity<JsonNode> responseEntity = restTemplate.postForEntity(url2,
                        entity,
                        JsonNode.class);
                System.out.println(responseEntity.getBody());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void hitRestWithRawHttp() {
        String url = "http://localhost:8082/consume-post";

        try {
            // Set connection
            URLConnection urlConn = new URL(url).openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;

            // autentikasi
//            httpConn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2Njc0NzI4NzQsImlhdCI6MTY2NzQ3Mjc1NH0.-NMVbOnWLWU8_1QKuxZmhJk_SKAd5wl9smzn9k5wf_gD-qTK_4ZQikYFGsgSivY5DojfzxlY10UgFj3Zl2fFMA");
//            httpConn.setRequestProperty("Authorization", "Basic dXNlcjpwYXNz"); // "user:pass" diubah jadi base64
            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Type", "application/json");
			httpConn.setRequestMethod("POST");
            System.out.println(httpConn.getRequestMethod());
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);


            // Set parameter / body
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            String jsonBodyStr = "{\n" +
                    "\t\"asd\":\"sad1\",\n" +
                    "\t\"key2\":\"val2\"\n" +
                    "}";
			writer.write(jsonBodyStr);
			writer.flush();


            // Read the response and write it to standard out.
            StringBuilder outputString = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String responseString;
            while ((responseString = br.readLine()) != null) {
                outputString.append(responseString);
            }
            httpConn.disconnect();
			System.out.println(outputString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hitRestWithRawHttp2() {
        String url = "http://localhost:8082/test-all-params-type/10";
        url += "?in1=testParam1";

        try {
            // Set connection
            URLConnection urlConn = new URL(url).openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) urlConn;

            // autentikasi
//            httpConn.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXZhaW51c2UiLCJleHAiOjE2Njc0NzI4NzQsImlhdCI6MTY2NzQ3Mjc1NH0.-NMVbOnWLWU8_1QKuxZmhJk_SKAd5wl9smzn9k5wf_gD-qTK_4ZQikYFGsgSivY5DojfzxlY10UgFj3Zl2fFMA");
//            httpConn.setRequestProperty("Authorization", "Basic dXNlcjpwYXNz"); // "user:pass" diubah jadi base64
            // Set the appropriate HTTP parameters.
            httpConn.setRequestProperty("Content-Type", "application/json");
			httpConn.setRequestMethod("POST");
            System.out.println(httpConn.getRequestMethod());
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);


            // Set parameter / body
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            String jsonBodyStr = "{\n" +
                    "\t\"asd\":\"sad1\",\n" +
                    "\t\"key2\":\"val2\"\n" +
                    "}";
            writer.write(jsonBodyStr);
            writer.flush();


            // Read the response and write it to standard out.
            StringBuilder outputString = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String responseString;
            while ((responseString = br.readLine()) != null) {
                outputString.append(responseString);
            }
            httpConn.disconnect();
            System.out.println(outputString);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
