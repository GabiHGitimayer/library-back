package com.biblioteca.seeders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class SeederCommand implements CommandLineRunner {

    @Autowired
    private UserSeeder userSeeder;

    @Autowired
    private BookSeeder bookSeeder;

    @Autowired
    private LoanSeeder loanSeeder;

    @Autowired
    private FineSeeder fineSeeder;

    @Autowired
    // private HistorySeeder historySeeder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Iniciando seeding de dados...");

        userSeeder.seedUsers();
        bookSeeder.seedBooks();
        loanSeeder.seedLoans();
        fineSeeder.seedFines();
        // historySeeder.seedHistory();

        System.out.println("Seeding concluído!");
    }
}
