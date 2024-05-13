package com.lorenzo.maidschallenge.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lorenzo.maidschallenge.model.Book;
import com.lorenzo.maidschallenge.model.BookStatus;
import com.lorenzo.maidschallenge.model.BorrowingRecord;
import com.lorenzo.maidschallenge.model.Patron;
import com.lorenzo.maidschallenge.repository.BookRepository;
import com.lorenzo.maidschallenge.repository.BorrowingRecordRepository;
import com.lorenzo.maidschallenge.repository.PatronRepository;
import com.lorenzo.maidschallenge.service.BookService;

public class BorrowingServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BorrowingService borrowingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBook_BookAndPatronExist_BookBorrowed() {
        // Arrange
        long bookId = 1L;
        long patronId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setStatus(BookStatus.AVAILABLE);

        Patron patron = new Patron();
        patron.setId(patronId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(bookService.updateBook(bookId, book)).thenReturn(book);

        // Act
        boolean result = borrowingService.borrowBook(bookId, patronId);

        // Assert
        assertTrue(result);
        assertEquals(BookStatus.BORROWED, book.getStatus());
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void returnBook_BookAndPatronExist_BookReturned() {
        // Arrange
        long bookId = 1L;
        long patronId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setStatus(BookStatus.BORROWED);

        Patron patron = new Patron();
        patron.setId(patronId);

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now().minusDays(10));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId))
                .thenReturn(Optional.of(borrowingRecord));
        when(bookService.updateBook(bookId, book)).thenReturn(book);


        // Act
        boolean result = borrowingService.returnBook(bookId, patronId);

        // Assert
        assertTrue(result);
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertNotNull(borrowingRecord.getReturnDate());
        assertTrue(borrowingRecord.getReturnDate().isAfter(borrowingRecord.getBorrowingDate()));
        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
//        verify(bookRepository, times(1)).save(book);
    }
}
