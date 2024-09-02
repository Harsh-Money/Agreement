package com.app.agreement.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OwnerProfile {
// id, Name, email, password, contact no.
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String email;
    private String password;
    private String contact_no;
    @OneToOne
    @JoinColumn(name = "shop_id")
    private OwnerShop ownerShop;

}
