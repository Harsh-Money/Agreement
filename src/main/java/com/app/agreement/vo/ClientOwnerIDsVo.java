package com.app.agreement.vo;

import lombok.Data;

@Data
public class ClientOwnerIDsVo {

    private Integer clientId;
    private String clientPassword;
    private Integer ownerId;
    private String ownerPassword;
}
