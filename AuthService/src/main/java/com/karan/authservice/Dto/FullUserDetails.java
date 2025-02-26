package com.karan.authservice.Dto;

import com.karan.authservice.entities.UserInfo;
import com.karan.authservice.entities.UserRoles;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FullUserDetails implements UserDetails {

    @Getter
    private final String userId;

    private final String username;
    private final String password;

    Collection<? extends GrantedAuthority> authorities;

    public FullUserDetails(UserInfo info) {
        this.userId = info.getUserId();
        this.username = info.getUsername();
        this.password = info.getPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();

        for(UserRoles role : info.getRoles()) {
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
