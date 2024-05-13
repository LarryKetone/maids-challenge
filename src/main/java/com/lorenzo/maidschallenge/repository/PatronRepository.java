package com.lorenzo.maidschallenge.repository;

import com.lorenzo.maidschallenge.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {
}
