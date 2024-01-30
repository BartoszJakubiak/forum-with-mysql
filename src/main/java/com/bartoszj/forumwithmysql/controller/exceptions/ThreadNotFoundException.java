package com.bartoszj.forumwithmysql.controller.exceptions;

public class ThreadNotFoundException extends Exception{
    private Long threadId;

    public ThreadNotFoundException(Long threadId) {
        this.threadId = threadId;
    }

    public Long getThreadId() {
        return threadId;
    }
}
