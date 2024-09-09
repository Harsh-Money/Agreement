package com.app.agreement.service;

import com.app.agreement.entity.ClientProfile;
import com.app.agreement.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientSecurityDetails implements UserDetailsService {

    @Autowired
    private ClientRepo clientRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientProfile clientProfile = clientRepo.findByNameIgnoreCase(username);
        if (clientProfile != null){
            return new UserPrincipal(clientProfile);
        }
        throw new UsernameNotFoundException("Client with given name not found!!!...");

    }
}
