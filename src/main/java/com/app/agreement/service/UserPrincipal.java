package com.app.agreement.service;

import com.app.agreement.entity.BaseEntityProfile;
import com.app.agreement.entity.ClientProfile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private BaseEntityProfile baseEntityProfile;

    public UserPrincipal(BaseEntityProfile baseEntityProfile) {
        this.baseEntityProfile = baseEntityProfile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority( "USER" ));
    }

    @Override
    public String getPassword() {
        return baseEntityProfile.getPassword();
    }

    @Override
    public String getUsername() {
        return baseEntityProfile.getName();
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
