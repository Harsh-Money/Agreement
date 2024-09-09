package com.app.agreement.service;

import com.app.agreement.entity.ClientProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private ClientProfile clientProfile;

    public UserPrincipal(ClientProfile clientProfile) {
        this.clientProfile = clientProfile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority( "USER" ));
    }

    @Override
    public String getPassword() {
        return clientProfile.getPassword();
    }

    @Override
    public String getUsername() {
        return clientProfile.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
