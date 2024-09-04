package com.app.agreement.dto;

import com.app.agreement.entity.BaseEntityProfile;
import com.app.agreement.util.OwnersShop;
import lombok.Data;

@Data
public class OwnerDto extends BaseEntityProfile {

    private OwnersShop ownersShop;
}
