package com.kevinAri.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kevinAri.example.util.CommonUtil;
import com.kevinAri.example.util.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AppService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ResponseHelper responseHelper;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    // fungsi untuk testing
    public Object test(JsonNode body) {
        try {
            return ResponseEntity.ok()
                    .body("ok");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body("Service failed");
        }
    }



    static class ResponseClass {
        public String output1 = "halo";
        public String output2 = "halo 2";
        private String output3 = "halo 3";
        protected String output4 = "halo 4";
        public static String output5 = "halo 5";
    }
    public Object testHttpServlet(HttpServletRequest request) {
        try {
            String requestBodyJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Map<String, Object> requestBody = objectMapper.readValue(requestBodyJson, Map.class);
            String option = (String) requestBody.get("option");

            // return JSON dengan berbagai cara
            if (option.equals("1")) {
                return Collections.singletonMap("response", "Hello World");
            }
            else if (option.equals("2")) {
                return "{\"output\":\"halo\"}";
            }
            else if (option.equals("3")) {
                return new ResponseClass();
            }
            else if (option.equals("4")) {
                return ResponseEntity.badRequest()
                        .header("Custom-Header", "foo")
                        .body("Custom header set");
            }
            else if (option.equals("5")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .header("Custom-Header", "foo")
                        .header("Custom-Header2", "foo")
                        .body(new ResponseClass());
            }
            else if (option.equals("6")) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Custom-Header", "foo");
                return new ResponseEntity<>(
                        "Custom header set", headers, HttpStatus.OK);
            }
            else if (option.equals("7")) {
                // untuk testing cek isi HttpServletRequest
                printInfoPenting (request);
                printInfoLain (request);
                return new ResponseEntity<>(
                        "Hello4 berhasil",
                        HttpStatus.OK);
            }
            else if (option.equals("8")) {
                // untuk testing cek isi HttpServletRequest
                printParam (request);
                return new ResponseEntity<>(
                        "Hello4 berhasil",
                        HttpStatus.OK);
            }
            else {
                return null;
            }
        } catch (Exception e) {
            CommonUtil.errorLog(log, e);
            return null;
        }
    }

    // print info dari HttpServletRequest
    private void printInfoPenting (HttpServletRequest request) {
        System.out.println("\nInfo Penting:");
        System.out.println("RemoteUser: " + request.getRemoteUser());
        System.out.println("Token: " + request.getHeader("Authorization").substring(7));
    }

    private void printInfoLain (HttpServletRequest request) {
        System.out.println("\nInfo dari HttpServletRequest:");
        System.out.println("AuthType: " + request.getAuthType());
        System.out.println("AttributeNames: " + request.getAttributeNames());
        System.out.println("LocalAddr: " + request.getLocalAddr());
        System.out.println("RemoteAddr: " + request.getRemoteAddr());
        System.out.println("Cookies: " + Arrays.toString(request.getCookies()));
        System.out.println("ContextPath: " + request.getContextPath());
        System.out.println("CharEncoding: " + request.getCharacterEncoding());
        System.out.println("RequestURI: " + request.getRequestURI());
        System.out.println("RequestURL: " + request.getRequestURL());
        System.out.println("ServletPath: " + request.getServletPath());
        System.out.println("UserPrincipal: " + request.getUserPrincipal());
        System.out.println("PathInfo: " + request.getPathInfo());
        System.out.println("Headers: " + request.getHeaderNames());
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            Enumeration<String> headers = request.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                System.out.printf("%s: %s\n", headerName, headers.nextElement());
            }
        }
        System.out.println("HeaderAuth: " + request.getHeader("Authorization"));
        // session methods
        HttpSession httpSession = request.getSession();
        System.out.println("Session: " + httpSession);
        // ServletContext
        ServletContext context = request.getSession().getServletContext();
        System.out.println("ServletContent: " + context);
        System.out.println();
    }

    private void printParam (HttpServletRequest request) {
        System.out.println("\nParameter:");
        System.out.println("QueryString: " + request.getQueryString());
        Enumeration <String> e = request.getParameterNames(); // get semua parameter
        String str;
        while (e.hasMoreElements()) {
            str = e.nextElement();
            System.out.println("Param Key: " + str);
        }
        System.out.println("Param Value: " + request.getParameter("key"));
        System.out.println("Param Value: " + request.getParameter("values"));
        String[] values = request.getParameterValues("values");
        System.out.println("Param Value: " + Arrays.toString(values));
        System.out.println("Param Length: " + values.length);
    }
}
