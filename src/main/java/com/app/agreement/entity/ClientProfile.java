package com.app.agreement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ClientProfile {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String email;
    private String password;
    private String contact_no;
}
