package com.bartoszj.forumwithmysql.model.threads;

import jakarta.validation.constraints.NotBlank;

public class ThreadDtoIn {
    @NotBlank(message = "title is required")
    private String title;

    public ThreadDtoIn() {
    }

    public String getTitle() {
        return this.title;
    }
}
