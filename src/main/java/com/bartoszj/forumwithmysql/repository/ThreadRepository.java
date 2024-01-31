package com.bartoszj.forumwithmysql.repository;

import com.bartoszj.forumwithmysql.model.comments.Comment;
import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.users.User;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends PagingAndSortingRepository<Thread, Long> {
    Optional<Thread> findThreadByTitle(String title);
    Page<Thread> findThreadByUser(User user, Pageable pageable);
    Optional<Thread> findById(Long id);
    Thread save(Thread thread);
}