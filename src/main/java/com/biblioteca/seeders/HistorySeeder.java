package com.biblioteca.seeders;

import com.biblioteca.entities.BookEntity;
import com.biblioteca.entities.HistoryEntity;
import com.biblioteca.entities.LoanEntity;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.repositories.BookRepository;
import com.biblioteca.repositories.HistoryRepository;
import com.biblioteca.repositories.LoanRepository;
import com.biblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

@Service
@Profile("dev")
public class HistorySeeder {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    public void seedHistory() {
        System.out.println("Populando tabela de histórico...");

        saveHistoryIfNotExists(1L, 1L, 1L, null, createDate(2024, 10, 1), createDate(2024, 10, 15));
        saveHistoryIfNotExists(2L, 3L, 2L, null, createDate(2024, 10, 5), createDate(2024, 10, 20));
        saveHistoryIfNotExists(3L, 2L, 3L, 3L, createDate(2024, 10, 10), createDate(2024, 10, 25));
        saveHistoryIfNotExists(4L, 4L, 4L, null, createDate(2024, 10, 12), createDate(2024, 10, 26));
    }

    private void saveHistoryIfNotExists(Long userId, Long bookId, Long loanId, Long returnId, Date loanDate,
            Date returnDate) {
        if (!historyRepository.existsByUserIdAndBookIdAndLoanId(userId, bookId, loanId)) {
            HistoryEntity history = new HistoryEntity();

            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + userId));
            BookEntity book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado: " + bookId));
            LoanEntity loan = loanRepository.findById(loanId)
                    .orElseThrow(() -> new IllegalArgumentException("Empréstimo não encontrado: " + loanId));

            history.setUserId(user);
            history.setBookId(book);
            history.setLoanId(loan);
            history.setReturnId(returnId); 
            history.setLoanDate(loanDate); 
            history.setReturnDate(returnDate); 

            historyRepository.save(history);
        }
    }

    private Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new java.sql.Date(calendar.getTimeInMillis());
    }
}
