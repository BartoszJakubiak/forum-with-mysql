package com.bartoszj.forumwithmysql.model.users;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    private Role() {
    }

    public String getAuthority() {
        return this.name();
    }
}
