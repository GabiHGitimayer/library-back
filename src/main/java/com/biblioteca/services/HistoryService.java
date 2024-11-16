package com.biblioteca.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.HistoryEntity;
import com.biblioteca.entities.FineEntity;
import com.biblioteca.repositories.HistoryRepository;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public void registerHistory(LoanEntity loan, FineEntity fine) {
        HistoryEntity history = new HistoryEntity();
        history.setUserId(loan.getUserId());
        history.setBookId(loan.getBookId());
        history.setLoanId(loan);

        if (loan.getLoanDate() != null) {
            history.setLoanDate(new Date(loan.getLoanDate().getTime()));
        }
        if (loan.getEfectiveReturnDate() != null) {
            history.setReturnDate(new Date(loan.getEfectiveReturnDate().getTime()));
        }
        
        historyRepository.save(history);
    }

    public void updateHistory(LoanEntity loan) {
        Optional<HistoryEntity> historyOptional = historyRepository.findByLoanId_loanId(loan.getLoanId());

        HistoryEntity history = historyOptional.orElseGet(HistoryEntity::new);
        history.setUserId(loan.getUserId());
        history.setBookId(loan.getBookId());
        history.setLoanId(loan);
        
        if (loan.getLoanDate() != null) {
            history.setLoanDate(new Date(loan.getLoanDate().getTime()));
        }
        
        if (loan.getEfectiveReturnDate() != null) {
            history.setReturnDate(new Date(loan.getEfectiveReturnDate().getTime()));
        }

        historyRepository.save(history);
    }
    
    public List<HistoryEntity> listHistory(Long userId) {
        return historyRepository.findByUserId_userId(userId);
    }
    
}