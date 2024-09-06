package com.app.agreement.entity;

import com.app.agreement.util.OwnersShop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class OwnerProfile extends BaseEntityProfile {

    @OneToMany(mappedBy = "ownerProfile")
    @JsonIgnore
    private List<AgreementEntity> agreements;

    @ManyToMany(mappedBy = "ownerProfiles")
    @JsonIgnore
    private List<ClientProfile> clientProfiles;

    @Embedded
    private OwnersShop ownersShop;

}
