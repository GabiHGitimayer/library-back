package com.biblioteca.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "history")
public class HistoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;
    
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserEntity userId;
    
    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    private BookEntity bookId;
    
    @ManyToOne
    @JoinColumn(name = "loanId", nullable = false)
    private LoanEntity loanId;
    
    //TODO: para que serve esse returnId?
    @ManyToOne
    @JoinColumn(name = "returnId")
    private LoanEntity returnId;
    
    @Column(name = "loanDate")
    private Date loanDate;
    
    @Column(name = "returnDate")
    private Date returnDate;
}
