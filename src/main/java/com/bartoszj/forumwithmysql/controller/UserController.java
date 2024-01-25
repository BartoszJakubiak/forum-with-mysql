package com.bartoszj.forumwithmysql.controller;

import com.bartoszj.forumwithmysql.model.users.Role;
import com.bartoszj.forumwithmysql.model.users.User;
import com.bartoszj.forumwithmysql.model.users.UserDtoIn;
import com.bartoszj.forumwithmysql.repository.UserRepository;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/user"})
//@Validated
public class UserController {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public UserController(PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @PostMapping({"/sign-up"})
    public String addUser(@Valid @RequestBody UserDtoIn newUserCredentials) {
        Optional<User> user = this.userRepository.findByUsername(newUserCredentials.getUsername());
        if (user.isPresent()) {
            return "User with this username already exists";
        } else {
            User newUser = new User();
            newUser.setUsername(newUserCredentials.getUsername());
            newUser.setPassword(this.encoder.encode(newUserCredentials.getPassword()));
            newUser.setRole("ROLE_USER");
            this.userRepository.save(newUser);
            return "Saved";
        }
    }
}
