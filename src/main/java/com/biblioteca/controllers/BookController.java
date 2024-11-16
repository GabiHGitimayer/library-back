package com.biblioteca.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.entities.BookEntity;
import com.biblioteca.services.BookService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService livroService;

    @PostMapping
    public ResponseEntity<BookEntity> save(@RequestBody BookEntity livro) {
        return ResponseEntity.ok(livroService.save(livro));
    }

    @GetMapping
    public ResponseEntity<List<BookEntity>> listAll() {
        return ResponseEntity.ok(livroService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> findById(@PathVariable Long id) {
        BookEntity livro = livroService.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));

        return ResponseEntity.ok(livro);
    }


    @PutMapping("/{id}")
    public ResponseEntity<BookEntity> update(@PathVariable Long id, @RequestBody BookEntity livro) {
        return livroService.findById(id)
                .map(existingLivro -> {
                    existingLivro.setTitle(livro.getTitle());
                    existingLivro.setAuthor(livro.getAuthor());
                    existingLivro.setGenre(livro.getGenre());
                    existingLivro.setIsbn(livro.getIsbn());
                    existingLivro.setPublicationYear(livro.getPublicationYear());
                    existingLivro.setCopiesQuantity(livro.getCopiesQuantity());
                    BookEntity updatedLivro = livroService.save(existingLivro);
                    return ResponseEntity.ok(updatedLivro);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

