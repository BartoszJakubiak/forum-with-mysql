package com.bartoszj.forumwithmysql.model.comments;

public class CommentDtoOut {
    private String comment;
    private String author;

    public CommentDtoOut(String content, String username) {
        this.comment = content;
        this.author = username;
    }

    public String getComment() {
        return this.comment;
    }

    public void setContent(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String username) {
        this.author = username;
    }
}
