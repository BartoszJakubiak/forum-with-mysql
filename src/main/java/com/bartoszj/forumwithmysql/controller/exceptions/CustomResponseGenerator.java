package com.bartoszj.forumwithmysql.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
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
