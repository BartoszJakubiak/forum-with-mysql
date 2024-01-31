package com.bartoszj.forumwithmysql.controller.responses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomResponseGenerator {
    public static ResponseEntity<Object> generateResponse(String msg, HttpStatus status, Object obj) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", msg);
        map.put("status", status.value());
        map.put("data", obj);
        return new ResponseEntity<>(map, status);
    }
    public static ResponseEntity<Object> generateResponseNoData(String msg, HttpStatus status) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("message", msg);
        map.put("status", status.value());
        return new ResponseEntity<>(map, status);
    }
}
