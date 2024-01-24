package com.bartoszj.forumwithmysql.repository;

import com.bartoszj.forumwithmysql.model.users.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}