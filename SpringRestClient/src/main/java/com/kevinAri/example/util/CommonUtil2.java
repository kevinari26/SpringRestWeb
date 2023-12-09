package com.kevinAri.example.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil2 {
    public static void errorMaker2() {
        try {
            Integer temp = 1/0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
