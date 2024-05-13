package com.lorenzo.maidschallenge.service;

import com.lorenzo.maidschallenge.exception.ResourceNotFoundException;
import com.lorenzo.maidschallenge.model.Book;
import com.lorenzo.maidschallenge.model.BookStatus;
import com.lorenzo.maidschallenge.repository.BookRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookServiceTest {


    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllBooks() {
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(Book.builder().id(1L).title("Book 1").isbn("1234567891234").publicationYear(2023).author("James Kow").status(BookStatus.AVAILABLE).build());
        mockBooks.add(Book.builder().id(2L).title("Book 2").isbn("1234567891255").publicationYear(2023).author("Haley Gi").status(BookStatus.AVAILABLE).build());
        mockBooks.add(Book.builder().id(3L).title("Book 3").isbn("1234567891266").publicationYear(2023).author("Henry Jun").status(BookStatus.AVAILABLE).build());

        when(bookRepository.findAll()).thenReturn(mockBooks);

        assertEquals(3,bookService.getAllBooks().size());

    }

    @Test
    void getBookById() {
        Book book1 = Book.builder().id(1L).title("Book 1").isbn("1234567891234")
                .publicationYear(2023).author("James Kow").status(BookStatus.AVAILABLE).build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));

        assertEquals(book1,bookService.getBookById(1L));

    }


    @Test
    void testGetBookById_NonExistingId_ThrowsException() {
        // Arrange
        long nonExistingId = 100L;

        when(bookRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            bookService.getBookById(nonExistingId);
        });
    }
}