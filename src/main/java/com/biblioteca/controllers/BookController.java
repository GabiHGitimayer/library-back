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

import com.biblioteca.dto.DeleteBookResponseDTO;
import com.biblioteca.dto.EditBookResponse;
import com.biblioteca.entities.BookEntity;
import com.biblioteca.services.BookService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookEntity>> listAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @PostMapping
    public ResponseEntity<BookEntity> save(@RequestBody BookEntity book) {
        return ResponseEntity.status(201).body(bookService.save(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> findById(@PathVariable Long id) {
        BookEntity book = bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado!"));

        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EditBookResponse> update(@PathVariable Long id, @RequestBody BookEntity book) {
        return bookService.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setGenre(book.getGenre());
                    existingBook.setIsbn(book.getIsbn());
                    existingBook.setPublicationYear(book.getPublicationYear());
                    existingBook.setCopiesQuantity(book.getCopiesQuantity());
                    BookEntity updatedBook = bookService.save(existingBook);
                    return ResponseEntity.ok(new EditBookResponse("Livro editado com sucesso! ", updatedBook));
                })
                .orElseGet(() -> ResponseEntity.status(204).body(new EditBookResponse("Livro não encontrado!", null)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteBookResponseDTO> delete(@PathVariable Long id) {
        String message = bookService.deleteById(id);
        return ResponseEntity.ok(new DeleteBookResponseDTO(message));
    }
}
