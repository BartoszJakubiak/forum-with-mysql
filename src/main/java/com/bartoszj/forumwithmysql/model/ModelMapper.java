package com.bartoszj.forumwithmysql.model;

import com.bartoszj.forumwithmysql.model.comments.Comment;
import com.bartoszj.forumwithmysql.model.comments.CommentDtoOut;
import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.threads.ThreadDtoOut;
import org.springframework.stereotype.Service;

@Service
public class ModelMapper {
    public ModelMapper() {
    }

    public CommentDtoOut commentToDtoOut(Comment comment) {
        return new CommentDtoOut(comment.getContent(),
                comment.getUser().getUsername());
    }

    public ThreadDtoOut threadToDtoOut(Thread thread) {
        return new ThreadDtoOut(thread.getId(),
                thread.getTitle(),
                thread.getUser().getUsername(),
                thread.getCommentList().stream().map(this::commentToDtoOut).limit(3).toList());
    }
}
