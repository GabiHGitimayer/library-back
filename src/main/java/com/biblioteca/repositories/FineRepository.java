package com.biblioteca.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.FineEntity;

@Repository
public interface FineRepository extends JpaRepository<FineEntity, Long> {
    List<FineEntity> findByUser_UserId(Long userId);
}
