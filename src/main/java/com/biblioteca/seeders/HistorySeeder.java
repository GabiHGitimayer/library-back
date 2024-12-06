// package com.biblioteca.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

// @Service
// @Profile("dev")
// public class HistorySeeder {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void seedHistory() {
        System.out.println("Populando tabela de histórico...");
        jdbcTemplate.update("INSERT INTO history (user_id, book_id, loan_id, return_id, loan_date, return_date) VALUES (1, 1, 1, NULL, '2024-10-01', '2024-10-15')");
        jdbcTemplate.update("INSERT INTO history (user_id, book_id, loan_id, return_id, loan_date, return_date) VALUES (2, 3, 2, NULL, '2024-10-05', '2024-10-20')");
        jdbcTemplate.update("INSERT INTO history (user_id, book_id, loan_id, return_id, loan_date, return_date) VALUES (3, 2, 3, 3, '2024-10-10', '2024-10-25')");
        jdbcTemplate.update("INSERT INTO history (user_id, book_id, loan_id, return_id, loan_date, return_date) VALUES (4, 4, 4, NULL, '2024-10-12', '2024-10-26')");
    }
}
