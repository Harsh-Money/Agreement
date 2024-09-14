package com.app.agreement.dto;

import com.app.agreement.entity.BaseEntityProfile;
import com.app.agreement.util.OwnersShop;

public class OwnerDto extends BaseEntityProfile {
    private OwnersShop ownersShop;

    public OwnersShop getOwnersShop() {
        return ownersShop;
    }

    public void setOwnersShop(OwnersShop ownersShop) {
        this.ownersShop = ownersShop;
    }


}
