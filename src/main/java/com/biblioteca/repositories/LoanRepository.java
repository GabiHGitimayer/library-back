package com.biblioteca.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import com.biblioteca.entities.LoanEntity;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    boolean existsByUserIdAndBookIdAndLoanDate(Long userId, Long bookId, Date loanDate);
}