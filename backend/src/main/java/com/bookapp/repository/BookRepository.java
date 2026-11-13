package com.bookapp.repository;

import com.bookapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByOwnerEmail(String email);

    Optional<Book> findByIdAndOwnerEmail(Long id, String email);
}
