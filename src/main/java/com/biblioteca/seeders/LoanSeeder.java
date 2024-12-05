package com.biblioteca.seeders;

import com.biblioteca.entities.BookEntity;
import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.repositories.BookRepository;
import com.biblioteca.repositories.LoanRepository;
import com.biblioteca.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Calendar;

@Service
@Profile("dev")
public class LoanSeeder {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public void seedLoans() {
        System.out.println("Populando tabela de empréstimos...");

        saveLoanIfNotExists(1L, 1L, createDate(2024, 10, 1), createDate(2024, 10, 15), "Emprestado");
        saveLoanIfNotExists(2L, 3L, createDate(2024, 10, 5), createDate(2024, 10, 20), "Emprestado");
        saveLoanIfNotExists(3L, 2L, createDate(2024, 10, 10), createDate(2024, 10, 25), "Devolvido");
        saveLoanIfNotExists(4L, 4L, createDate(2024, 10, 12), createDate(2024, 10, 26), "Emprestado");
    }

    private void saveLoanIfNotExists(Long userId, Long bookId, Date loanDate, Date returnDate, String status) {
        if (!loanRepository.existsByUserIdAndBookIdAndLoanDate(userId, bookId, loanDate)) {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + userId));

            BookEntity book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado: " + bookId));

            LoanEntity loan = new LoanEntity();
            loan.setUserId(user);
            loan.setBookId(book);
            loan.setLoanDate(loanDate);
            loan.setReturnDate(returnDate);
            loan.setLoanStatus(status);
            loanRepository.save(loan);
        }
    }

    private Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
