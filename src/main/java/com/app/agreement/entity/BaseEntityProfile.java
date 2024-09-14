package com.app.agreement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntityProfile {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String email;
    private String password;
    private String contact_no;
    private List<String> roles;
}
