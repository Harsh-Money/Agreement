package com.app.agreement.service;

import com.app.agreement.dto.ClientDto;
import com.app.agreement.dto.ClientOwnerAgreementDtoIDs;
import com.app.agreement.dto.OwnerDto;
import com.app.agreement.entity.AgreementEntity;
import com.app.agreement.entity.ClientProfile;
import com.app.agreement.entity.OwnerProfile;
import com.app.agreement.repository.AgreementRepo;
import com.app.agreement.repository.ClientRepo;
import com.app.agreement.repository.OwnerRepo;
import com.app.agreement.util.AgreementStatus;
import com.app.agreement.util.ClientAgreement;
import com.app.agreement.util.JWTToken;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepo clientRepo;

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

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);



    public List<ClientProfile> getAllClient() throws Exception {
//        ClientProfile clientProfile = clientRepo.findByNameIgnoreCase(name);
//        System.out.println(clientProfile.getName());
        return clientRepo.findAll();
    }

    @Override
    public ClientProfile getClientByName(String clientName) throws Exception {
        ClientProfile clientProfile = clientRepo.findByNameIgnoreCase(clientName);
        return clientProfile;
    }

    @Transactional
    public Boolean setClientDetails(ClientDto clientDto) throws Exception{
        ClientProfile clientProfile = new ClientProfile();
        clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        clientDto.setRoles(Arrays.asList("client"));
        BeanUtils.copyProperties(clientDto, clientProfile);
        clientRepo.save(clientProfile);
        return true;
    }

    @Override
    public Boolean updateClientDetails(ClientDto clientDto) throws Exception {
        ClientProfile clientProfile = new ClientProfile();
        if(clientDto.getPassword() != null){
            clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
        }
        BeanUtils.copyProperties(clientDto,clientProfile);
        int id = clientRepo.findByNameIgnoreCase(clientDto.getName()).getId();
        int result = clientRepo.updateClientProfile(id, clientDto);
        if(result > 0){
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean deleteClientExist(Integer id) throws Exception {
        int result = clientRepo.deleteByClientId(id);
        if (result > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean setClientOwnerAgreement(ClientOwnerAgreementDtoIDs clientOwnerAgreementDtoIDs) throws Exception {
        ClientProfile clientProfile = clientRepo.findById(clientOwnerAgreementDtoIDs.getClientId()).get();
        OwnerProfile ownerProfile = ownerRepo.findById(clientOwnerAgreementDtoIDs.getOwnerId()).get();

        OwnerDto ownerDto = new OwnerDto();
        BeanUtils.copyProperties(ownerProfile, ownerDto);


        clientProfile.getOwnerProfiles().add(ownerProfile);
        clientRepo.save(clientProfile);
        return true;
    }

    @Override
    public JWTToken verify(ClientDto clientDto) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(clientDto.getName(), clientDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            jwtToken.setToken(jwtService.getToken(clientDto.getName()));
            return jwtToken;
        }
        else
            return null;
    }

    @Override
    public Boolean sendAgreementToOwner(ClientAgreement clientAgreement) {
        LocalDateTime dateTimeAfter24hrs  = LocalDateTime.now().plus(24, ChronoUnit.HOURS);
        OwnerProfile ownerProfile = ownerRepo.findById(clientAgreement.getOwnerId()).get();
        ClientProfile clientProfile = clientRepo.findById(clientAgreement.getClientId()).get();
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setName(clientAgreement.getAgreementName());
        agreementEntity.setCloudinaryUrl(clientAgreement.getCludinaryUrl());
        agreementEntity.setCreatedTimestamp(LocalDateTime.now());
        agreementEntity.setExpireTimestamp(dateTimeAfter24hrs);
        agreementEntity.setStatus(AgreementStatus.APPLIED);
        agreementEntity.setClientProfile(clientProfile);
        agreementEntity.setOwnerProfile(ownerProfile);
        agreementRepo.save(agreementEntity);
        return true;
    }

}
