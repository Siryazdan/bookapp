package com.bookapp.controller;

import com.bookapp.model.Book;
import com.bookapp.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // The endpoints are protected by SecurityConfig, so "auth" is always the logged-in user here.

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book, Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book, auth.getName()));
    }

    @GetMapping
    public List<Book> getUserBooks(Authentication auth) {
        return bookService.getBooks(auth.getName());
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id, Authentication auth) {
        return bookService.getBook(id, auth.getName());
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook, Authentication auth) {
        return bookService.updateBook(id, updatedBook, auth.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id, Authentication auth) {
        bookService.deleteBook(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
}
