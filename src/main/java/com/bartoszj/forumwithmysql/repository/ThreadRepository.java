package com.bartoszj.forumwithmysql.repository;

import com.bartoszj.forumwithmysql.model.threads.Thread;
import com.bartoszj.forumwithmysql.model.users.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends CrudRepository<Thread, Long> {
    Optional<Thread> findThreadByTitle(String title);

    Iterable<Thread> findThreadByUser(User user);
}