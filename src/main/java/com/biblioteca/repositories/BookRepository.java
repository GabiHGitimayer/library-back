package com.biblioteca.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.entities.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
