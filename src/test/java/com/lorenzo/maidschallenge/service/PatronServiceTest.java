package com.lorenzo.maidschallenge.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.lorenzo.maidschallenge.exception.ResourceNotFoundException;
import com.lorenzo.maidschallenge.model.Patron;
import com.lorenzo.maidschallenge.repository.PatronRepository;

public class PatronServiceTest {

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private PatronService patronService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPatrons() {
        List<Patron> mockPatrons = new ArrayList<>();
        mockPatrons.add(Patron.builder()
                .id(1L)
                .contactInformation("00222334333")
                .name("Joy")
                .build());

        mockPatrons.add(Patron.builder()
                .id(2L)
                .contactInformation("00222334555")
                .name("James")
                .build());

        when(patronRepository.findAll()).thenReturn(mockPatrons);

        List<Patron> result = patronService.getAllPatrons();

        assertEquals(mockPatrons, result);
    }

    @Test
    void testGetPatronById_ExistingId_ReturnsPatron() {
        long patronId = 1L;
        Patron mockPatron = Patron.builder()
                .id(1L)
                .contactInformation("00222334555")
                .name("James")
                .build();

        when(patronRepository.findById(patronId)).thenReturn(Optional.of(mockPatron));

        Patron result = patronService.getPatronById(patronId);

        assertEquals(mockPatron, result);
    }

    @Test
    void testGetPatronById_NonExistingId_ThrowsException() {

        long nonExistingId = 100L;

        when(patronRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            patronService.getPatronById(nonExistingId);
        });
    }

}
