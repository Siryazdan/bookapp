package com.bookapp.repository;

import com.bookapp.model.Book;
import com.bookapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByOwnerEmail(String email);
}
