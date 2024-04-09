package com.bartoszj.forumwithmysql.model.threads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ThreadDtoIn {
    @NotBlank(message = "title is required")
    @Pattern(regexp = "^[a-zA-Z0-9,.:;<>?!() _-]{0,255}$", message = "Character limit is 255. Please use only letters, digits and ,.:;/<>?!()_-")
    private String title;

    public ThreadDtoIn() {
    }

    public String getTitle() {
        return this.title;
    }
}
