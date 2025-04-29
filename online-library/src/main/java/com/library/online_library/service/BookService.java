package com.library.online_library.service;

import com.library.online_library.dto.book.BookRequestDTO;
import com.library.online_library.dto.book.BookResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookResponseDTO addBook(BookRequestDTO dto);
    Page<BookResponseDTO> getAvailableBooks(Pageable pageable);
    List<BookResponseDTO> searchBooks(String author, Integer year);
}