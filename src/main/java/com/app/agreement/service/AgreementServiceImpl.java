package com.app.agreement.service;

import com.app.agreement.dto.AgreementDto;
import com.app.agreement.dto.ClientOwnerAgreementDtoIDs;
import com.app.agreement.dto.ClientOwnerIDsDto;
import com.app.agreement.entity.AgreementEntity;
import com.app.agreement.entity.ClientProfile;
import com.app.agreement.entity.OwnerProfile;
import com.app.agreement.repository.AgreementRepo;
import com.app.agreement.repository.ClientRepo;
import com.app.agreement.repository.OwnerRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService{

    @Autowired
    private AgreementRepo agreementRepo;

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private OwnerRepo ownerRepo;

    @Override
    public List<AgreementEntity> getAllAgreement() throws Exception {
        return agreementRepo.findAll();
    }

    @Override
    public Boolean setAgreementDetails(AgreementDto agreementDto) throws Exception {
        AgreementEntity agreementEntity = new AgreementEntity();
        BeanUtils.copyProperties(agreementDto, agreementEntity);
        agreementRepo.save(agreementEntity);
        return true;
    }

    @Override
    public Boolean updateAgreementDetails(AgreementDto agreementDto) throws Exception {
        AgreementEntity agreementEntity = new AgreementEntity();
        BeanUtils.copyProperties(agreementDto, agreementEntity);
        int id = agreementDto.getId();
        int result = agreementRepo.updateOwnerProfile(id, agreementDto);
        if(result > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean deleteAgreementExist(Integer id) throws Exception {
        int result = agreementRepo.deleteByOwnerId(id);
        if(result > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean setNewAgreement(ClientOwnerAgreementDtoIDs clientOwnerAgreementDtoIDs) throws Exception {
        ClientProfile clientProfile = clientRepo.findById(clientOwnerAgreementDtoIDs.getClientId()).get();
        OwnerProfile ownerProfile = ownerRepo.findById(clientOwnerAgreementDtoIDs.getOwnerId()).get();
        AgreementEntity agreementEntity = agreementRepo.findById(clientOwnerAgreementDtoIDs.getAgreementId()).get();

        agreementEntity.setClientProfile(clientProfile);
        agreementEntity.setOwnerProfile(ownerProfile);
        agreementRepo.save(agreementEntity);
        return true;
    }

    @Override
    public Boolean getAgreementSigned(ClientOwnerIDsDto clientOwnerIDsDto) throws Exception {
        ClientProfile clientProfile = clientRepo.findById(clientOwnerIDsDto.getClientId()).get();
        OwnerProfile ownerProfile = ownerRepo.findById(clientOwnerIDsDto.getOwnerId()).get();
        if(clientOwnerIDsDto.getClientPassword() == clientProfile.getPassword()
                && clientOwnerIDsDto.getOwnerPassword() == ownerProfile.getPassword()) {
            return true;
        }
        return false;
    }
}
