package com.app.agreement.service;

import com.app.agreement.dto.OwnerDto;
import com.app.agreement.entity.OwnerProfile;

import java.util.List;

public interface OwnerService {

    public List<OwnerProfile> getAllOwner() throws Exception;
    public Boolean setOwnerDetails(OwnerDto ownerDto)throws Exception;
    public Boolean updateOwnerDetails(OwnerDto ownerDto)throws Exception;
    public Boolean deleteOwnerExist(Integer id)throws Exception;
}
