package com.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.biblioteca.entities.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByUserCpf(String userCpf);
    boolean existsByEmail(String email);
}
