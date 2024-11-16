package com.biblioteca.services;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.FineEntity;
import com.biblioteca.repositories.FineRepository;

@Service
public class FineService {

    @Autowired
    private FineRepository fineRepository;

    public FineEntity calculateFine(LoanEntity loan) {
        Date today = new Date();
        Long daysLate = (today.getTime() - loan.getReturnDate().getTime()) / (1000 * 60 * 60 * 24);
        
        FineEntity fine = new FineEntity();
        fine.setLoan(loan);
        fine.setCalculationDate(today);

        if (daysLate > 0) {
            BigDecimal finePerDay = new BigDecimal("5.00");
            fine.setFineValue(finePerDay.multiply(BigDecimal.valueOf(daysLate)));
        } else {
            fine.setFineValue(BigDecimal.ZERO);
        }

        return fineRepository.save(fine);
    }

    
    public FineEntity updateFine(Long id, FineEntity fineUpdated) {
        FineEntity existentFine = fineRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Multa não encontrada"));

        existentFine.setFineValue(fineUpdated.getFineValue());
        existentFine.setCalculationDate(fineUpdated.getCalculationDate());

        return fineRepository.save(existentFine);
    }

}
