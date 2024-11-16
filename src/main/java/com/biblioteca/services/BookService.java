package com.biblioteca.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.entities.BookEntity;
import com.biblioteca.repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    public Optional<BookEntity> findById(Long id) {
        return bookRepository.findById(id);
    }


    public BookEntity save(BookEntity livro) {
        return bookRepository.save(livro);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
