package com.app.agreement.repository;

import com.app.agreement.dto.AgreementDto;
import com.app.agreement.entity.AgreementEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgreementRepo extends JpaRepository<AgreementEntity, Integer> {

    List<AgreementEntity> findAll();

//    private String name;
//    private String cloudinaryUrl;
//    private Date createdTimestamp;
//    private Date modifiedTimestamp;
//    private String status;
    @Modifying
    @Transactional
    @Query("UPDATE AgreementEntity ae " +
            "SET ae.name = COALESCE(:#{#agreementDto.name}, ae.name), " +
            "    ae.cloudinaryUrl = COALESCE(:#{#agreementDto.cloudinaryUrl}, ae.cloudinaryUrl), " +
            "    ae.createdTimestamp = COALESCE(:#{#agreementDto.createdTimestamp}, ae.createdTimestamp), " +
            "    ae.modifiedTimestamp = COALESCE(:#{#agreementDto.modifiedTimestamp}, ae.modifiedTimestamp), " +
            "    ae.status = COALESCE(:#{#agreementDto.status}, ae.status) " +
            "WHERE ae.id = :id")
    int updateOwnerProfile(@Param("id") Integer id, @Param("agreementDto") AgreementDto agreementDto);

    @Modifying
    @Transactional
    @Query("delete from AgreementEntity ae where ae.id = :id")
    int deleteByOwnerId(int id);
}
