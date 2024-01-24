package com.bartoszj.forumwithmysql.model.users;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
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
        return this.user.getRole();
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
