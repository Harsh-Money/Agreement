package com.app.agreement.service;

import com.app.agreement.dto.ClientDto;
import com.app.agreement.dto.ClientOwnerAgreementDtoIDs;
import com.app.agreement.dto.OwnerDto;
import com.app.agreement.entity.ClientProfile;
import com.app.agreement.entity.OwnerProfile;
import com.app.agreement.repository.ClientRepo;
import com.app.agreement.repository.OwnerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private OwnerRepo ownerRepo;



    public List<ClientProfile> getAllClient() throws Exception {
        String name = "Ram";
        List<ClientProfile> clientProfile = clientRepo.findByNameIgnoreCase(name);
        System.out.println(clientProfile.get(0).getName());
        return clientRepo.findAll();
    }

    @Override
    public ClientProfile getClientByName(String clientName) throws Exception {
        List<ClientProfile> clientProfile = clientRepo.findByNameIgnoreCase(clientName);
        return null;
    }

    @Transactional
    public Boolean setClientDetails(ClientDto clientDto) throws Exception{
        ClientProfile clientProfile = new ClientProfile();
//        clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
//        clientDto.setRoles(Arrays.asList("client"));
        BeanUtils.copyProperties(clientDto, clientProfile);
        System.out.println(clientProfile.getPassword());
        clientRepo.save(clientProfile);
        return true;
    }

    @Override
    public Boolean updateClientDetails(ClientDto clientDto) throws Exception {
        ClientProfile clientProfile = new ClientProfile();
//        if(clientDto.getPassword() != null){
//            clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
//        }
        BeanUtils.copyProperties(clientDto,clientProfile);
        int id = clientDto.getId();
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

}
