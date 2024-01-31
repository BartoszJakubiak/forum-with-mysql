package com.bartoszj.forumwithmysql.repository;

import com.bartoszj.forumwithmysql.model.comments.Comment;
import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findCommentsByThread(Thread thread, Pageable pageable);
    Page<Comment> findCommentsByUser(User user, Pageable pageable);
    Comment save(Comment comment);
}
