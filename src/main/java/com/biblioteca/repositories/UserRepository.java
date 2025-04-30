package com.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.biblioteca.entities.UserEntity;
import com.google.common.base.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserDetails findByUserCpf(String userCpf);


    Optional<UserEntity> findByUserName(String userName);
}

