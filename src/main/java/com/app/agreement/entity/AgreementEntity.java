package com.app.agreement.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AgreementEntity extends BaseEntityAgreement {

    @ManyToOne
    @JoinColumn(name = "clientprofile_id")
    private ClientProfile clientProfile;

    @ManyToOne
    @JoinColumn(name = "ownerProfile_id")
    private OwnerProfile ownerProfile;

}
