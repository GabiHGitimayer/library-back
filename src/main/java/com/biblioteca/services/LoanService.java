package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.BookEntity;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.repositories.LoanRepository;
import com.biblioteca.repositories.BookRepository;
import com.biblioteca.repositories.UserRepository;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private FineService fineService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private BookRepository bookRepository;

    public LoanEntity doLoan(LoanEntity loan) {
        UserEntity user = userRepository.findById(loan.getUserId().getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        BookEntity book = bookRepository.findById(loan.getBookId().getBookId())
                .orElseThrow(() -> new RuntimeException("book não encontrado"));

        if (book.getCopiesQuantity() <= 0) {
            throw new RuntimeException("Não há exemplares disponíveis para empréstimo");
        }
        if (loan.getLoanDate() == null) {
            throw new IllegalArgumentException("A data do empréstimo deve ser informada.");
        }
        if (loan.getReturnDate() == null) {
            loan.setReturnDate(loan.calculateReturnDate(loan.getLoanDate(), 7));
        }

        book.setCopiesQuantity(book.getCopiesQuantity() - 1);
        bookRepository.save(book);
        loan.setUserId(user);
        loan.setBookId(book);
        LoanEntity saved = loanRepository.save(loan);
        historyService.registerHistory(saved, null);

        return saved;
    }

    public List<LoanEntity> findAll() {
        List<LoanEntity> loans = loanRepository.findAll();
        loans.forEach(this::verifyStatusAndCalculateFine);

        return loans;
    }

    public LoanEntity updateLoan(Long id, LoanEntity updatedLoan) {
        LoanEntity existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        existingLoan.setReturnDate(updatedLoan.getReturnDate());
        existingLoan.setLoanStatus(updatedLoan.getLoanStatus());

        return loanRepository.save(existingLoan);
    }

    public Optional<LoanEntity> findById(Long id) {
        Optional<LoanEntity> loan = loanRepository.findById(id);
        loan.ifPresent(this::verifyStatusAndCalculateFine);

        return loan;
    }

    public LoanEntity makeReturn(Long loanId) {
        LoanEntity loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));

        if ("Devolvido".equals(loan.getLoanStatus())) {
            throw new RuntimeException("Este empréstimo já foi devolvido");
        }

        loan.makeReturn();
        BookEntity book = loan.getBookId();
        book.setCopiesQuantity(book.getCopiesQuantity() + 1);
        bookRepository.save(book);

        if (loan.getEfectiveReturnDate().after(loan.getReturnDate())) {
            fineService.calculateFine(loan);
        }

        LoanEntity updatedLoan = loanRepository.save(loan);
        historyService.updateHistory(updatedLoan);

        return updatedLoan;
    }

    public void deleteById(Long id) {
        loanRepository.deleteById(id);
    }

    private void verifyStatusAndCalculateFine(LoanEntity loan) {
        loan.verifyStatus();
        if ("Atrasado".equals(loan.getLoanStatus())) {
            fineService.calculateFine(loan);
        }
    }
}
