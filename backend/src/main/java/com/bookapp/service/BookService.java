package com.bookapp.service;

import com.bookapp.exception.ApiException;
import com.bookapp.model.Book;
import com.bookapp.model.User;
import com.bookapp.repository.BookRepository;
import com.bookapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * All methods work only on the books of the given user (identified by email),
 * so nobody can read, change or delete another user's books.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public Book createBook(Book book, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "User not found"));
        book.setId(null); // never let the client choose/overwrite an ID
        book.setOwner(owner);
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks(String ownerEmail) {
        return bookRepository.findByOwnerEmail(ownerEmail);
    }

    @Transactional(readOnly = true)
    public Book getBook(Long id, String ownerEmail) {
        return bookRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    @Transactional
    public Book updateBook(Long id, Book updatedData, String ownerEmail) {
        Book existing = getBook(id, ownerEmail);
        existing.setTitle(updatedData.getTitle());
        existing.setAuthor(updatedData.getAuthor());
        existing.setGenre(updatedData.getGenre());
        existing.setPublicationYear(updatedData.getPublicationYear());
        existing.setDescription(updatedData.getDescription());
        existing.setCoverImageUrl(updatedData.getCoverImageUrl());
        return bookRepository.save(existing);
    }

    @Transactional
    public void deleteBook(Long id, String ownerEmail) {
        bookRepository.delete(getBook(id, ownerEmail));
    }
}
