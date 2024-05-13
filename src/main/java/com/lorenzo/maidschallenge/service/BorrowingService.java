package com.lorenzo.maidschallenge.service;


import com.lorenzo.maidschallenge.exception.ResourceNotFoundException;
import com.lorenzo.maidschallenge.model.Book;
import com.lorenzo.maidschallenge.model.BookStatus;
import com.lorenzo.maidschallenge.model.BorrowingRecord;
import com.lorenzo.maidschallenge.model.Patron;
import com.lorenzo.maidschallenge.repository.BookRepository;
import com.lorenzo.maidschallenge.repository.BorrowingRecordRepository;
import com.lorenzo.maidschallenge.repository.PatronRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BorrowingService {
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final PatronRepository patronRepository;
    private final BorrowingRecordRepository borrowingRecordRepository;


    @Transactional
    public boolean borrowBook(Long bookId, Long patronId) {
        boolean bookBorrowed = false;
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId, HttpStatus.NOT_FOUND.value()));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + patronId, HttpStatus.NOT_FOUND.value()));

        if (book.getStatus() == BookStatus.AVAILABLE) {
            BorrowingRecord borrowingRecord = new BorrowingRecord();
            borrowingRecord.setBook(book);
            borrowingRecord.setPatron(patron);
            borrowingRecord.setBorrowingDate(LocalDate.now());
            borrowingRecordRepository.save(borrowingRecord);
            book.setStatus(BookStatus.BORROWED);
            bookService.updateBook(bookId, book);
            return true;
        }
        return bookBorrowed;
    }

    @Transactional
    public boolean returnBook(Long bookId, Long patronId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId, HttpStatus.NOT_FOUND.value()));
        patronRepository.findById(patronId)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + patronId, HttpStatus.NOT_FOUND.value()));

        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new ResourceNotFoundException("BorrowingRecord not found with bookId : " + bookId
                        + ", patronId : " + patronId, HttpStatus.NOT_FOUND.value()));


        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);
        book.setStatus(BookStatus.AVAILABLE);
        bookService.updateBook(bookId, book);
        return borrowingRecord.getReturnDate().isAfter(borrowingRecord.getBorrowingDate());

    }
}
