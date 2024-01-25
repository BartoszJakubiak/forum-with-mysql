package com.bartoszj.forumwithmysql.model.comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CommentDtoIn {
    @NotNull(message = "thread ID is required")
    private Long threadId;
    @NotEmpty(message = "comment content is required")
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
