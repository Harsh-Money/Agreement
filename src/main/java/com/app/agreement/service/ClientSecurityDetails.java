package com.app.agreement.service;

import com.app.agreement.entity.ClientProfile;
import com.app.agreement.entity.OwnerProfile;
import com.app.agreement.repository.ClientRepo;
import com.app.agreement.repository.OwnerRepo;
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

    @Autowired
    private OwnerRepo ownerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientProfile clientProfile = clientRepo.findByNameIgnoreCase(username);
        OwnerProfile ownerProfile = ownerRepo.findByNameIgnoreCase(username);
        if (clientProfile != null){
            return new UserPrincipal(clientProfile);
        }
        if (ownerProfile != null){
            return new UserPrincipal(ownerProfile);
        }
        throw new UsernameNotFoundException("Client with given name not found!!!...");

    }
}
