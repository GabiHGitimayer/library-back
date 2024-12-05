package com.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.entities.LoanEntity;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
}