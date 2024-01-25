package com.bartoszj.forumwithmysql.model.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

//@Valid
public class UserDtoIn {
    @NotBlank(message = "username is required")
    private String username;
    @NotEmpty(message = "password is required")
    private String password;

    public UserDtoIn() {
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}
