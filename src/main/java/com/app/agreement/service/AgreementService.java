package com.app.agreement.service;

import com.app.agreement.dto.AgreementDto;
import com.app.agreement.dto.ClientOwnerAgreementDtoIDs;
import com.app.agreement.dto.ClientOwnerIDsDto;
import com.app.agreement.entity.AgreementEntity;

import java.util.List;

public interface AgreementService {

    public List<AgreementEntity> getAllAgreement() throws Exception;
    public Boolean setAgreementDetails(AgreementDto agreementDto)throws Exception;
    public Boolean updateAgreementDetails(AgreementDto agreementDto)throws Exception;
    public Boolean deleteAgreementExist(Integer id)throws Exception;
    public Boolean setNewAgreement(ClientOwnerAgreementDtoIDs clientOwnerAgreementDtoIDs)throws Exception;
    public Boolean getAgreementSigned(ClientOwnerIDsDto clientOwnerIDsDto)throws Exception;

}
