package com.biblioteca.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class LoanSeeder {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void seedLoans() {
        System.out.println("Populando tabela de empréstimos...");
        jdbcTemplate.update("INSERT INTO loan (user_id, book_id, loan_date, return_date, loan_status) VALUES (1, 1, '2024-10-01', '2024-10-15', 'ACTIVE')");
        jdbcTemplate.update("INSERT INTO loan (user_id, book_id, loan_date, return_date, loan_status) VALUES (2, 3, '2024-10-05', '2024-10-20', 'ACTIVE')");
        jdbcTemplate.update("INSERT INTO loan (user_id, book_id, loan_date, return_date, loan_status) VALUES (3, 2, '2024-10-10', '2024-10-25', 'RETURNED')");
        jdbcTemplate.update("INSERT INTO loan (user_id, book_id, loan_date, return_date, loan_status) VALUES (4, 4, '2024-10-12', '2024-10-26', 'ACTIVE')");
    }
}
