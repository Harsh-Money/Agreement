package com.app.agreement.entity;

import com.app.agreement.dto.OwnerDto;
import com.app.agreement.util.AgreementStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ClientProfile extends BaseEntityProfile {

    @OneToMany(mappedBy = "clientProfile")
    @JsonIgnore
    private List<AgreementEntity> agreements;

    @ManyToMany
    @JoinTable(name = "client_owner",
                joinColumns = {@JoinColumn(name = "client_id")},
                inverseJoinColumns = {@JoinColumn(name = "owner_id")}
    )
    private List<OwnerProfile> ownerProfiles;


}
