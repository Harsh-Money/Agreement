package com.app.agreement.repository;

import com.app.agreement.dto.ClientDto;
import com.app.agreement.entity.ClientProfile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepo extends JpaRepository<ClientProfile, Integer> {

    List<ClientProfile> findAll();


    ClientProfile findByNameIgnoreCase( String name);

//    @Query("select cp from ClientProfile cp where cp.id = :id")
//    ClientProfile findByClientId(Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE ClientProfile cp " +
            "SET cp.name = COALESCE(:#{#clientDto.name}, cp.name), " +
            "    cp.email = COALESCE(:#{#clientDto.email}, cp.email), " +
            "    cp.password = COALESCE(:#{#clientDto.password}, cp.password), " +
            "    cp.contact_no = COALESCE(:#{#clientDto.contact_no}, cp.contact_no) " +
            "WHERE cp.id = :id")
    int updateClientProfile(@Param("id") Integer id, @Param("clientDto") ClientDto clientDto);

    @Modifying
    @Transactional
    @Query("delete from ClientProfile cp where cp.id = :id")
    int deleteByClientId(int id);
}
