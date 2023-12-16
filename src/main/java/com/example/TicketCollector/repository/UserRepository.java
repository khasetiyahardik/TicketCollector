package com.example.TicketCollector.repository;


import com.example.TicketCollector.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = "SELECT COUNT(*) FROM user_entity WHERE contact_number=:adminContactNumber OR email=:adminEmail OR name=:adminName", nativeQuery = true)
    Long findByNoAndEmail(String adminContactNumber, String adminEmail, String adminName);

    @Query(value = "SELECT * FROM user_entity WHERE email=:username", nativeQuery = true)
    Optional<UserEntity> findOneByEmailIgnoreCase(String username);
    @Query(value = "SELECT * FROM user_entity WHERE id=:adminId",nativeQuery = true)
    Optional<UserEntity> findByAdminId(Long adminId);

    @Query(value = "SELECT * FROM user_entity WHERE email=:email AND password=:password",nativeQuery = true)
    UserEntity findByNameAndPassword(String email, String password);
    @Query(value = "SELECT * FROM user_entity WHERE email=:email AND is_verified is true", nativeQuery = true)
    UserEntity findByVerifiedEmail(String email);
}