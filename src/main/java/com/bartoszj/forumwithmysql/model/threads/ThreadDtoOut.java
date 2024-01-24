package com.bartoszj.forumwithmysql.model.threads;

import com.bartoszj.forumwithmysql.model.comments.CommentDtoOut;
import java.util.List;

public class ThreadDtoOut {
    private Long threadId;
    private String title;
    private String author;
    private List<CommentDtoOut> commentDtoOutList;

    public ThreadDtoOut(Long threadId, String title, String author, List<CommentDtoOut> commentDtoOutList) {
        this.threadId = threadId;
        this.title = title;
        this.author = author;
        this.commentDtoOutList = commentDtoOutList;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<CommentDtoOut> getCommentDtoOutList() {
        return this.commentDtoOutList;
    }

    public void setCommentDtoOutList(List<CommentDtoOut> commentDtoOutList) {
        this.commentDtoOutList = commentDtoOutList;
    }

    public Long getThreadId() {
        return this.threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }
}
