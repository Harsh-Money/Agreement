package com.app.agreement.service;

import com.app.agreement.dto.ClientDto;
import com.app.agreement.dto.ClientOwnerAgreementDtoIDs;
import com.app.agreement.dto.OwnerDto;
import com.app.agreement.entity.ClientProfile;
import com.app.agreement.util.ClientAgreement;
import com.app.agreement.util.JWTToken;

import java.util.List;

public interface ClientService {

    public List<ClientProfile> getAllClient() throws Exception;
    public ClientProfile getClientByName(String clientName)throws Exception;
    public Boolean setClientDetails(ClientDto clientDto)throws Exception;
    public Boolean updateClientDetails(ClientDto clientDto)throws Exception;
    public Boolean deleteClientExist(Integer id)throws Exception;
    public Boolean setClientOwnerAgreement(ClientOwnerAgreementDtoIDs clientOwnerAgreementDtoIDs)throws Exception;

    JWTToken verify(ClientDto clientDto);

    Boolean sendAgreementToOwner(ClientAgreement clientAgreement)throws Exception;
}
