package com.app.agreement.repository;

import com.app.agreement.dto.OwnerDto;
import com.app.agreement.entity.OwnerProfile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepo extends JpaRepository<OwnerProfile, Integer> {

    List<OwnerProfile> findAll();

    @Query("SELECT op FROM OwnerProfile op WHERE op.name = :name")
    OwnerProfile findByNameIgnoreCase(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE OwnerProfile op " +
            "SET op.name = COALESCE(:#{#ownerDto.name}, op.name), " +
            "    op.email = COALESCE(:#{#ownerDto.email}, op.email), " +
            "    op.password = COALESCE(:#{#ownerDto.password}, op.password), " +
            "    op.contact_no = COALESCE(:#{#ownerDto.contact_no}, op.contact_no) " +
            "WHERE op.id = :id")
    int updateOwnerProfile(@Param("id") Integer id, @Param("ownerDto") OwnerDto ownerDto);

    @Modifying
    @Transactional
    @Query("delete from OwnerProfile op where op.id = :id")
    int deleteByOwnerId(int id);
}
