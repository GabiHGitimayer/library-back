package com.biblioteca.seeders;

import com.biblioteca.entities.BookEntity;
import com.biblioteca.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class BookSeeder {

    @Autowired
    private BookRepository bookRepository;

    public void seedBooks() {
        System.out.println("Populando tabela de livros...\n");

        saveBookIfNotExists("O Senhor dos Anéis", "J.R.R. Tolkien", "Fantasia", "978-3-16-148410-0", 1954, 10);
        saveBookIfNotExists("1984", "George Orwell", "Distopia", "978-0-452-28423-4", 1949, 8);
        saveBookIfNotExists("A Brief History of Time", "Stephen Hawking", "Ciência", "978-0-553-17521-9", 1988, 5);
        saveBookIfNotExists("O Hobbit", "J.R.R. Tolkien", "Fantasia", "978-0-345-39934-7", 1937, 6);
    }

    private void saveBookIfNotExists(String title, String author, String genre, String isbn, int publicationYear,
            int copiesQuantity) {
        if (!bookRepository.existsByTitle(title)) {
            BookEntity book = new BookEntity();
            book.setTitle(title);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setIsbn(isbn);
            book.setPublicationYear(publicationYear);
            book.setCopiesQuantity(copiesQuantity);
            bookRepository.save(book);
        }
    }
}
