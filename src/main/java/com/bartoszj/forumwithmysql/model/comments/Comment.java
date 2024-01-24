package com.bartoszj.forumwithmysql.model.comments;

import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private @NotBlank String content;
    @ManyToOne
    @JoinColumn(
            name = "USER_ID"
    )
    private @NotNull User user;
    @ManyToOne
    @JoinColumn(
            name = "THREAD_ID"
    )
    private @NotNull Thread thread;

    public Comment() {
    }

    public Comment(String content, User user, Thread thread) {
        this.content = content;
        this.user = user;
        this.thread = thread;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
