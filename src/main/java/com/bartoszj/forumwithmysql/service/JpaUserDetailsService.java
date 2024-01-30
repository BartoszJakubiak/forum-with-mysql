package com.bartoszj.forumwithmysql.service;

import com.bartoszj.forumwithmysql.model.users.UserSecurity;
import com.bartoszj.forumwithmysql.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .map(UserSecurity::new)
                .orElseThrow(() -> {
            return new UsernameNotFoundException("User not found " + username);
        });
    }
}
