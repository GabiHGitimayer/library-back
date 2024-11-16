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
    private BookRepository livroRepository;

    public List<BookEntity> findAll() {
        return livroRepository.findAll();
    }

 // LivroService.java
    public Optional<BookEntity> findById(Long id) {
        return livroRepository.findById(id);
    }


    public BookEntity save(BookEntity livro) {
        return livroRepository.save(livro);
    }

    public void deleteById(Long id) {
        livroRepository.deleteById(id);
    }
}
