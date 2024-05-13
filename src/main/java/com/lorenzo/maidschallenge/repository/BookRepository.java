package com.lorenzo.maidschallenge.repository;

import com.lorenzo.maidschallenge.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
