package com.app.agreement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OwnerShop {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String workPerform;
    @OneToOne
    @JoinColumn(name = "owner_id")
    private OwnerProfile ownerProfile;
}
