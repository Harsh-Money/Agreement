package com.app.agreement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ClientAgreement {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String CloudinaryUrl;
    private Date createdTimestamp;
    private Date modifiedTimestamp;
    private String status;
}
