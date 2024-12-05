package com.biblioteca.seeders;

import com.biblioteca.entities.FineEntity;
import com.biblioteca.repositories.FineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

@Service
@Profile("dev")
public class FineSeeder {

    @Autowired
    private FineRepository fineRepository;

    public void seedFines() {
        System.out.println("Populando tabela de multas...\n");

        saveFineIfNotExists(3L, new BigDecimal("15.50"), createDate(2024, Calendar.OCTOBER, 15));
        saveFineIfNotExists(4L, new BigDecimal("10.00"), createDate(2024, Calendar.OCTOBER, 16));
    }

    private void saveFineIfNotExists(Long loanId, BigDecimal fineValue, Date calculationDate) {
        if (!fineRepository.existsByLoanIdAndCalculationDate(loanId, calculationDate)) {
            FineEntity fine = new FineEntity();
            fine.setFineId(loanId);
            fine.setFineValue(fineValue);
            fine.setCalculationDate(calculationDate);
            fineRepository.save(fine);
        }
    }

    private Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
