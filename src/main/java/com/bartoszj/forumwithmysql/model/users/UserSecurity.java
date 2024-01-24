package com.bartoszj.forumwithmysql.model.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserSecurity implements UserDetails {
    private User user;

    public UserSecurity(User user) {
        this.user = user;
    }

    public String getUsername() {
        return this.user.getUsername();
    }

    public String getPassword() {
        return this.user.getPassword();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user
                .getRole()
                .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
