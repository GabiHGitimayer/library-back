package com.biblioteca.dto;

import com.biblioteca.entities.BookEntity;

public record EditBookResponse(String message, BookEntity book) {
}