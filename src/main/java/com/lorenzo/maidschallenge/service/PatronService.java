package com.lorenzo.maidschallenge.service;

import com.lorenzo.maidschallenge.annotation.LogExecutionTime;
import com.lorenzo.maidschallenge.exception.ResourceNotFoundException;
import com.lorenzo.maidschallenge.model.Patron;
import com.lorenzo.maidschallenge.repository.PatronRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @LogExecutionTime
    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    @Cacheable("patrons")
    public Patron getPatronById(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patron not found with id: " + id, HttpStatus.NOT_FOUND.value()));
    }

    @Transactional
    @LogExecutionTime
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Transactional
    @LogExecutionTime
    public Patron updatePatron(Long id, Patron patron) {
        Patron existingPatron = getPatronById(id);
        existingPatron.setName(patron.getName());
        existingPatron.setContactInformation(patron.getContactInformation());
        return patronRepository.save(existingPatron);
    }

    public void deletePatron(Long id) {
        Patron patron = getPatronById(id);
        patronRepository.delete(patron);
    }
}
