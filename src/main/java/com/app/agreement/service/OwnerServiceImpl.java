package com.app.agreement.service;

import com.app.agreement.dto.OwnerDto;
import com.app.agreement.entity.OwnerProfile;
import com.app.agreement.repository.OwnerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepo ownerRepo;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTService jwtService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<OwnerProfile> getAllOwner() throws Exception {
        return ownerRepo.findAll();
    }

    @Override
    @Transactional
    public Boolean setOwnerDetails(OwnerDto ownerDto) throws Exception {
        OwnerProfile ownerProfile = new OwnerProfile();
        ownerDto.setPassword(passwordEncoder.encode(ownerDto.getPassword()));
        ownerDto.setRoles(Arrays.asList("owner"));
        BeanUtils.copyProperties(ownerDto, ownerProfile);
        ownerRepo.save(ownerProfile);
        return true;
    }

    @Override
    public Boolean updateOwnerDetails(OwnerDto ownerDto) throws Exception {
        OwnerProfile ownerProfile = new OwnerProfile();
        System.out.println(ownerDto.getPassword()+" inside service impl of owner");
        if (ownerDto.getPassword() != null) {
            ownerDto.setPassword(passwordEncoder.encode(ownerDto.getPassword()));
        }
        BeanUtils.copyProperties(ownerDto,ownerProfile);
//        int id = jwtService.extractId()
        int id = ownerRepo.findByNameIgnoreCase(ownerDto.getName()).getId();
        System.out.println(id+" "+ownerDto);
        int result = ownerRepo.updateOwnerProfile(id, ownerDto);
        if (result > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteOwnerExist(Integer id) throws Exception {
        int result = ownerRepo.deleteByOwnerId(id);
        if (result > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String verify(OwnerDto ownerDto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(ownerDto.getName(), ownerDto.getPassword())
        );

        if (auth.isAuthenticated()) {
            return jwtService.getToken(ownerDto.getName());
        }
        return "failed";
    }
}
