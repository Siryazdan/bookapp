package com.bookapp.controller;

import com.bookapp.model.Book;
import com.bookapp.model.User;
import com.bookapp.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // ✅ Create a new book for the logged-in user
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String email = auth.getName(); //
        User user = bookService.getUserByEmail(email);
        if (user == null) return ResponseEntity.status(401).build();

        book.setOwner(user);
        Book created = bookService.createBook(book);
        return ResponseEntity.ok(created);
    }



    // ✅ Get all books of the current user
    @GetMapping
    public ResponseEntity<List<Book>> getUserBooks() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String email = auth.getName();
        List<Book> userBooks = bookService.getBooksByEmail(email);

        return ResponseEntity.ok(userBooks);
    }


    // ✅ Get a specific book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update a book (ownership check can be added later)
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookService.getBookById(id)
                .map(existing -> {
                    Book updated = bookService.updateBook(existing, updatedBook);
                    return ResponseEntity.ok(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    // ✅ Delete a book (ownership check can be added later)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(existing -> {
                    bookService.deleteBook(id);
                    return ResponseEntity.ok().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
