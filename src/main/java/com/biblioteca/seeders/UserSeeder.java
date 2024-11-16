package com.biblioteca.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class UserSeeder {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();

    public void seedUsers() {
        System.out.println("Populando tabela de usuários...");

        if (!userExists("admin@library.com")) {
            jdbcTemplate.update(
                    "INSERT INTO user (user_name, user_cpf, user_password, user_type, user_email) VALUES (?, ?, ?, ?, ?)",
                    "Admin", "98765432100", passEncoder.encode("Admin"), "ADMIN", "admin@library.com");
        }

        if (!userExists("joao_silva@library.com")) {
            jdbcTemplate.update(
                    "INSERT INTO user (user_name, user_cpf, user_password, user_type, user_email) VALUES (?, ?, ?, ?, ?)",
                    "João Silva", "12345678901", passEncoder.encode("senha123"), "ADMIN", "joao_silva@library.com");
        }

        if (!userExists("maria_oliveria@library.com")) {
            jdbcTemplate.update(
                    "INSERT INTO user (user_name, user_cpf, user_password, user_type, user_email) VALUES (?, ?, ?, ?, ?)",
                    "Maria Oliveira", "12341234123", passEncoder.encode("senha456"), "USER",
                    "maria_oliveria@library.com");
        }

        if (!userExists("carlos_souza@library.com")) {
            jdbcTemplate.update(
                    "INSERT INTO user (user_name, user_cpf, user_password, user_type, user_email) VALUES (?, ?, ?, ?, ?)",
                    "Carlos Souza", "11223344556", passEncoder.encode("senha789"), "EMPLOYEE",
                    "carlos_souza@library.com");
        }

        if (!userExists("ana_costa@library.com")) {
            jdbcTemplate.update(
                    "INSERT INTO user (user_name, user_cpf, user_password, user_type, user_email) VALUES (?, ?, ?, ?, ?)",
                    "Ana Costa", "55667788999", passEncoder.encode("senha101"), "USER", "ana_costa@library.com");
        }
    }

    private boolean userExists(String email) {
        @SuppressWarnings("deprecation")
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM user WHERE user_email = ?",
                new Object[] { email }, Integer.class);
        return count != null && count > 0;
    }
}
