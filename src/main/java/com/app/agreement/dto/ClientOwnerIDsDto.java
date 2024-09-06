package com.app.agreement.dto;

import lombok.Data;

@Data
public class ClientOwnerIDsDto {

    private Integer clientId;
    private String clientPassword;
    private Integer ownerId;
    private String ownerPassword;
}
