package com.library.online_library.repository;

import com.library.online_library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByPublishedYear(Integer year);

    Page<Book> findByAvailableCopiesGreaterThan(int availableCopies, Pageable pageable);


    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    Page<Book> findByPublishedYear(Integer year, Pageable pageable);

    Page<Book> findByAuthorContainingIgnoreCaseAndPublishedYear(String author, Integer year, Pageable pageable);

    Page<Book> findByAuthorContainingIgnoreCaseAndPublishedYearAndAvailableCopiesGreaterThan(
            String author, Integer year, int availableCopies, Pageable pageable);

    Page<Book> findByAuthorContainingIgnoreCaseAndAvailableCopiesGreaterThan(
            String author, int availableCopies, Pageable pageable);

    Page<Book> findByPublishedYearAndAvailableCopiesGreaterThan(
            Integer year, int availableCopies, Pageable pageable);

}
