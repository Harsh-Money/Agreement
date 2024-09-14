package com.app.agreement.service;

import com.app.agreement.dto.OwnerDto;
import com.app.agreement.entity.AgreementEntity;
import com.app.agreement.entity.OwnerProfile;
import com.app.agreement.repository.AgreementRepo;
import com.app.agreement.repository.OwnerRepo;
import com.app.agreement.util.AgreementStatus;
import com.app.agreement.util.JWTToken;
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

    @Autowired
    private JWTToken jwtToken;

    @Autowired
    private AgreementRepo agreementRepo;

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
    public JWTToken verify(OwnerDto ownerDto) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(ownerDto.getName(), ownerDto.getPassword())
        );

        if (auth.isAuthenticated()) {
            jwtToken.setToken(jwtService.getToken(ownerDto.getName()));
            return jwtToken;
        }
        return null;
    }

    @Override
    public Boolean setAgreementApprove(Integer id) throws Exception {
        AgreementEntity agreementEntity = agreementRepo.findById(id).get();
        agreementEntity.setStatus(AgreementStatus.APPROVED);
        agreementRepo.save(agreementEntity);
        return true;
    }

    @Override
    public OwnerProfile getOwnerByName(String name)throws Exception {
        OwnerProfile ownerProfile = ownerRepo.findByNameIgnoreCase(name);
        return ownerProfile;
    }
}
