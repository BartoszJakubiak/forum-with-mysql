package com.bartoszj.forumwithmysql.model.comments;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CommentDtoIn {
    @NotNull(message = "thread ID is required")
    private Long threadId;
    @NotEmpty(message = "comment content is required")
    @Pattern(regexp = "^[a-zA-Z0-9,.:;<>?!() _-]{0,255}$", message = "Character limit is 255. Please use only letters, digits and ,.:;/<>?!()_-")
    private String content;

    public CommentDtoIn() {
    }

    public String getContent() {
        return this.content;
    }

    public Long getThreadId() {
        return this.threadId;
    }
}
