package com.kevinAri.example.util;

import org.slf4j.Logger;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CommonUtil {
    public static Date currentDateTime() {
        return new Date(System.currentTimeMillis());
    }
    public static XMLGregorianCalendar currentDateTimeXml() {
        try {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(currentDateTime());
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            return null;
        }
    }


    // logging
    public static void infoLog(Logger log, String title, String request) {
        log.info(String.format("\n=== %s ===", title));
        log.info(log.getName());
        log.info("request: {}", request);
    }

    public static void errorLog(Logger log, Exception e) {
        log.error("\n=== Error Log ===");
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
    }
}
