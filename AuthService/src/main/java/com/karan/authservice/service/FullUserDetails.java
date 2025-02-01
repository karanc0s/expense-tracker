package com.karan.authservice.service;

import com.karan.authservice.entities.UserInfo;
import com.karan.authservice.entities.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FullUserDetails extends UserInfo implements UserDetails {

    private final String username;
    private final String password;

    Collection<? extends GrantedAuthority> authorities;

    public FullUserDetails(UserInfo info) {
        this.username = info.getUsername();
        this.password = info.getPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();

        for(UserRole role : info.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName().toUpperCase()));
        }
        this.authorities = authorities;

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
