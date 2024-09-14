package com.app.agreement.util;

import lombok.Data;

@Data
public class ClientAgreement {

    private String agreementName;
    private Integer clientId;
    private Integer ownerId;
    private String clientEmail;
    private String cludinaryUrl;
    private String ownerEmail;

}
