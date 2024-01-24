package com.bartoszj.forumwithmysql.repository;

import com.bartoszj.forumwithmysql.model.comments.Comment;
import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Iterable<Comment> findCommentsByThread(Thread thread);

    Iterable<Comment> findCommentsByUser(User user);
}
