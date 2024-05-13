package com.lorenzo.maidschallenge.service;

import com.lorenzo.maidschallenge.annotation.LogExecutionTime;
import com.lorenzo.maidschallenge.exception.ResourceNotFoundException;
import com.lorenzo.maidschallenge.model.Book;
import com.lorenzo.maidschallenge.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;


    @LogExecutionTime
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Cacheable("books")
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id, HttpStatus.NOT_FOUND.value()));
    }

    @Transactional
    @LogExecutionTime
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setPublicationYear(book.getPublicationYear());
        existingBook.setIsbn(book.getIsbn());
        return bookRepository.save(existingBook);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

}
