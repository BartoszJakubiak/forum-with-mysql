package com.bartoszj.forumwithmysql.controller;

import com.bartoszj.forumwithmysql.controller.responses.CustomResponseGenerator;
import com.bartoszj.forumwithmysql.model.users.User;
import com.bartoszj.forumwithmysql.model.users.UserDtoIn;
import com.bartoszj.forumwithmysql.repository.UserRepository;

import java.util.Optional;

import com.bartoszj.forumwithmysql.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
//@Validated
public class UserController {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserController(PasswordEncoder encoder, UserRepository userRepository, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDtoIn newUserCredentials) {
        Optional<User> user = this.userRepository.findByUsername(newUserCredentials.getUsername());
        if (user.isPresent()) {
            return CustomResponseGenerator
                    .generateResponseNoData("User with this username already exists",
                            HttpStatus.BAD_REQUEST);
        } else {
            User newUser = new User();
            newUser.setUsername(newUserCredentials.getUsername());
            newUser.setPassword(this.encoder.encode(newUserCredentials.getPassword()));
            newUser.setRole("ROLE_USER");
            this.userRepository.save(newUser);
            return CustomResponseGenerator
                    .generateResponseNoData("New user added",
                            HttpStatus.CREATED);
        }
    }

    @PostMapping("/token")
    public ResponseEntity<Object> token(@RequestBody UserDtoIn userDtoIn){
         Authentication auth = authenticationManager
                 .authenticate(new UsernamePasswordAuthenticationToken(userDtoIn.getUsername(),
                        userDtoIn.getPassword()));
        return CustomResponseGenerator
                .generateResponseNoData(tokenService.generateToken(auth),
                        HttpStatus.ACCEPTED);
    }
}
