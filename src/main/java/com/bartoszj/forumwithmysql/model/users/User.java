package com.bartoszj.forumwithmysql.model.users;

import com.bartoszj.forumwithmysql.model.comments.Comment;
import com.bartoszj.forumwithmysql.model.threads.Thread;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String role;
    @OneToMany
    @JoinColumn(
            name = "USER_ID",
            referencedColumnName = "ID"
    )
    private List<Thread> threadsList;
    @OneToMany
    @JoinColumn(
            name = "USER_ID",
            referencedColumnName = "ID"
    )
    private List<Comment> commentList;

    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void addThread(Thread thread) {
        this.threadsList.add(thread);
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
