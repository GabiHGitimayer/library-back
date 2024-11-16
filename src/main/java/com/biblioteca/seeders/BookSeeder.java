package com.biblioteca.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class BookSeeder {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void seedBooks() {
        System.out.println("Populando tabela de livros...");

        if (!bookExists("O Senhor dos Anéis")) {
            jdbcTemplate.update(
                    "INSERT INTO book (title, author, genre, isbn, publication_year, copies_quantity) VALUES ('O Senhor dos Anéis', 'J.R.R. Tolkien', 'Fantasia', '978-3-16-148410-0', 1954, 10)");
        }
        if (!bookExists("1984")) {
            jdbcTemplate.update(
                    "INSERT INTO book (title, author, genre, isbn, publication_year, copies_quantity) VALUES ('1984', 'George Orwell', 'Distopia', '978-0-452-28423-4', 1949, 8)");
        }
        if (!bookExists("A Brief History of Time")) {
            jdbcTemplate.update(
                    "INSERT INTO book (title, author, genre, isbn, publication_year, copies_quantity) VALUES ('A Brief History of Time', 'Stephen Hawking', 'Ciência', '978-0-553-17521-9', 1988, 5)");
        }
        if (!bookExists("O Hobbit")) {
            jdbcTemplate.update(
                "INSERT INTO book (title, author, genre, isbn, publication_year, copies_quantity) VALUES ('O Hobbit', 'J.R.R. Tolkien', 'Fantasia', '978-0-345-39934-7', 1937, 6)");
        }
    }

    private boolean bookExists(String book) {
        @SuppressWarnings("deprecation")
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM book WHERE title = ?",
                new Object[] { book }, Integer.class);
        return count != null && count > 0;
    }
}
