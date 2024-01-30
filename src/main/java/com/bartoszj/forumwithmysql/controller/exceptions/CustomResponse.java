package com.bartoszj.forumwithmysql.controller.exceptions;

public class CustomResponse {
    public String response;

    public CustomResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
