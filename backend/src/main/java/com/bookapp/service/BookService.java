package com.bookapp.service;

import com.bookapp.model.Book;
import com.bookapp.model.User;
import com.bookapp.repository.BookRepository;
import com.bookapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository; // ✅ needed to fetch user by email

    public Book createBook(Book book) {
        System.out.println("📥 Saving book: " + book.getTitle());
        Book saved = bookRepository.save(book);
        System.out.println("✅ Book saved with ID: " + saved.getId());
        return saved;
    }

    public List<Book> getBooksByEmail(String email) {
        return bookRepository.findByOwnerEmail(email);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book updateBook(Book existingBook, Book updatedData) {
        existingBook.setTitle(updatedData.getTitle());
        existingBook.setAuthor(updatedData.getAuthor());
        existingBook.setGenre(updatedData.getGenre());
        existingBook.setPublicationYear(updatedData.getPublicationYear());
        existingBook.setDescription(updatedData.getDescription());
        existingBook.setCoverImageUrl(updatedData.getCoverImageUrl());
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // ✅ NEW: get user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
