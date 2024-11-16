package com.biblioteca.entities;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "loan")
public class LoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity userId;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private BookEntity bookId;

    @Column(name = "loanDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date loanDate;

    @Column(name = "returnDate")
    @Temporal(TemporalType.DATE)
    private Date returnDate;

    @Column(name = "efectiveReturnDate")
    @Temporal(TemporalType.DATE)
    private Date efectiveReturnDate;

    @Column(name = "loanStatus")
    private String loanStatus;
    
    @PrePersist
    public void prePersist() {
        this.loanStatus = "Emprestado";
        if (this.loanDate == null) {
            throw new IllegalArgumentException("A data do empréstimo deve ser informada.");
        }
        if (this.returnDate == null) {
            this.returnDate = calcularDataDevolucao(this.loanDate, 7);
        }
    }

    public Date calcularDataDevolucao(Date initialDate, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(initialDate);
        int addedDays = 0;

        while (addedDays < days) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            int weekDays = cal.get(Calendar.DAY_OF_WEEK);
            if (weekDays != Calendar.SATURDAY && weekDays != Calendar.SUNDAY) {
                addedDays++;
            }
        }

        return cal.getTime();
    }

    public void verificarStatus() {
        if (this.loanStatus.equals("Devolvido")) {
            return;
        }
        
        Date today = new Date();
        if (today.after(returnDate)) {
            loanStatus = "Atrasado";
        } else {
            loanStatus = "No Prazo";
        }
    }

    public void realizarDevolucao() {
        this.efectiveReturnDate = new Date();
        this.loanStatus = "Devolvido";
    }
}
