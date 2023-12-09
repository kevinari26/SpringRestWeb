package com.kevinAri.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;

public class CommonUtil {
    public static void errorMaker() {
        try {
//        String bigDecStr = null;
//        BigDecimal bigDecimal = new BigDecimal(bigDecStr);
//        Integer temp = 1/0;
            CommonUtil2.errorMaker2();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readValue("12345:'asd'", Object.class);
        } catch (Exception e) {
            Integer temp = 1/0;
        }
    }
    private static void errorMaker2() {
        Integer temp = 1/0;
    }

    // log
    public static void errorLog(Logger log, Exception e) {
        log.error("");
        log.error("=== Error Log ===");
        log.error("Cause: {}", e.toString());
        for (StackTraceElement stack : e.getStackTrace()) {
            log.error("Stack: {}", stack);
        }
        Throwable rootCause = e.getCause();
        while (rootCause!=null) {
            log.error("RootCause: {}", rootCause.toString());
            for (StackTraceElement stack : rootCause.getStackTrace()) {
                log.error("Stack: {}", stack);
            }
            rootCause = rootCause.getCause();
        }
        log.error("");
    }
}
