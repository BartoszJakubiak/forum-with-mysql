package com.bartoszj.forumwithmysql.controller.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CustomResponseGenerator {
    public static ResponseEntity<Object> generateResponse(String msg, HttpStatus status, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("status", status.value());
        map.put("data", obj);
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponseNoData(String msg, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        map.put("status", status.value());
        return new ResponseEntity<>(map, status);
    }
}
