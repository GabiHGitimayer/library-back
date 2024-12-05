package com.biblioteca.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class FineSeeder {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void seedFines() {
        System.out.println("Populando tabela de multas...");
        jdbcTemplate.update("INSERT INTO fine (loan, fine_value, calculation_date) VALUES (3, 15.50, '2024-10-15')");
        jdbcTemplate.update("INSERT INTO fine (loan, fine_value, calculation_date) VALUES (4, 10.00, '2024-10-16')");
    }
}
