package com.biblioteca.entities;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fine")
public class FineEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    @ManyToOne
    @JoinColumn(name = "loan", nullable = false)
    private LoanEntity loan;

    @Column(name = "fineValue", nullable = false)
    private BigDecimal fineValue;

    @Column(name = "calculationDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date calculationDate;

    public void calcularMulta() {
        Date today = new Date();
        long daysLate = (today.getTime() - loan.getEfectiveReturnDate().getTime()) / (1000 * 60 * 60 * 24);
        if (daysLate > 0) {
            BigDecimal finePerDay = new BigDecimal("5.00");
            fineValue = finePerDay.multiply(BigDecimal.valueOf(daysLate));
            calculationDate = today;
        } else {
            fineValue = BigDecimal.ZERO;
        }
    }
}
