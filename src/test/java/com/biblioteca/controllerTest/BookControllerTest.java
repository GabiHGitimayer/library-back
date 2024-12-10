package com.biblioteca.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.biblioteca.controllers.BookController;
import com.biblioteca.dto.EditBookResponse;
import com.biblioteca.entities.BookEntity;
import com.biblioteca.services.BookService;

import java.util.Optional;

@SpringBootTest
public class BookControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Test
    void testUpdateBook() {
        Long bookId = 1L;
        BookEntity existingBook = new BookEntity();
        existingBook.setBookId(bookId);
        existingBook.setTitle("Old Title");
        existingBook.setAuthor("Old Author");

        BookEntity bookToUpdate = new BookEntity();
        bookToUpdate.setTitle("New Title");
        bookToUpdate.setAuthor("New Author");

        EditBookResponse expectedResponse = new EditBookResponse("Livro editado com sucesso! ", existingBook);

        when(bookService.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookService.save(any(BookEntity.class))).thenReturn(existingBook);

        ResponseEntity<EditBookResponse> response = bookController.update(bookId, bookToUpdate);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Livro editado com sucesso! ", response.getBody().message());
        assertEquals(existingBook, response.getBody().book());
    }

    @Test
    void testUpdateBookNotFound() {
        Long bookId = 1L;
        BookEntity bookToUpdate = new BookEntity();
        bookToUpdate.setTitle("New Title");
        bookToUpdate.setAuthor("New Author");

        when(bookService.findById(bookId)).thenReturn(Optional.empty());

        ResponseEntity<EditBookResponse> response = bookController.update(bookId, bookToUpdate);

        assertEquals(204, response.getStatusCodeValue());
        assertEquals("Livro não encontrado!", response.getBody().message());
    }

    @Test
    void testSaveBook() {
        BookEntity bookToSave = new BookEntity();
        bookToSave.setTitle("New Book");
        bookToSave.setAuthor("Author Name");
        bookToSave.setGenre("Genre");
        bookToSave.setIsbn("123456789");
        bookToSave.setPublicationYear(2024);
        bookToSave.setCopiesQuantity(5);

        BookEntity savedBook = new BookEntity();
        savedBook.setBookId(1L);
        savedBook.setTitle("New Book");
        savedBook.setAuthor("Author Name");
        savedBook.setGenre("Genre");
        savedBook.setIsbn("123456789");
        savedBook.setPublicationYear(2024);
        savedBook.setCopiesQuantity(5);

        when(bookService.save(bookToSave)).thenReturn(savedBook);

        ResponseEntity<BookEntity> response = bookController.save(bookToSave);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(savedBook.getTitle(), response.getBody().getTitle());
        assertEquals(savedBook.getAuthor(), response.getBody().getAuthor());
    }
}
