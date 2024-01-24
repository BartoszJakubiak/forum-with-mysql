package com.bartoszj.forumwithmysql.model.comments;

public class CommentDtoIn {
    private Long threadId;
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
