package com.bartoszj.forumwithmysql.model.threads;

import com.bartoszj.forumwithmysql.model.comments.Comment;
import com.bartoszj.forumwithmysql.model.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Thread {
    @Id
    @GeneratedValue
    private Long id;
    private @NotBlank String title;
    @ManyToOne
    @JoinColumn(
            name = "USER_ID"
    )
    private @NotNull User user;
    @OneToMany
    @JoinColumn(
            name = "THREAD_ID",
            referencedColumnName = "ID"
    )
    private List<Comment> commentList;

    public Thread() {
    }

    public String getTitle() {
        return this.title;
    }

    public User getUser() {
        return this.user;
    }

    public Long getId() {
        return this.id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Comment> getCommentList() {
        return this.commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }
}
