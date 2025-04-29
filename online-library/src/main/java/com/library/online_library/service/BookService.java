package com.library.online_library.service;

import com.library.online_library.dto.book.BookRequestDTO;
import com.library.online_library.dto.book.BookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookService {
    BookResponseDTO addBook(BookRequestDTO dto);

    Page<BookResponseDTO> getAvailableBooks(Pageable pageable);

    Page<BookResponseDTO> searchBooks(String author, Integer year, Pageable pageable);

    Page<BookResponseDTO> searchAllBooks(String author, Integer year, Pageable pageable);
}