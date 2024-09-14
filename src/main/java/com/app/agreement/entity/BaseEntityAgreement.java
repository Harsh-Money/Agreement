package com.app.agreement.entity;

import com.app.agreement.util.AgreementStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEntityAgreement {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String cloudinaryUrl;
    private LocalDateTime createdTimestamp;
    private LocalDateTime expireTimestamp;
    private AgreementStatus status;
}
