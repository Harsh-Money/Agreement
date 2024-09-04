package com.app.agreement.vo;

import com.app.agreement.entity.BaseEntityProfile;
import com.app.agreement.util.OwnersShop;
import lombok.Data;

@Data
public class OwnerVo extends BaseEntityProfile {


    private OwnersShop ownersShop;
}
