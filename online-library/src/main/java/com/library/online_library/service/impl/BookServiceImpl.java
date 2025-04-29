package com.library.online_library.service.impl;

import com.library.online_library.dto.book.BookRequestDTO;
import com.library.online_library.dto.book.BookResponseDTO;
import com.library.online_library.entity.Book;
import com.library.online_library.repository.BookRepository;
import com.library.online_library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookResponseDTO addBook(BookRequestDTO dto) {
        Book book = modelMapper.map(dto, Book.class);
        return modelMapper.map(bookRepository.save(book), BookResponseDTO.class);
    }

    @Override
    public Page<BookResponseDTO> getAvailableBooks(Pageable pageable) {
        return bookRepository.findByAvailableCopiesGreaterThan(0, pageable)
                .map(book -> modelMapper.map(book, BookResponseDTO.class));
    }

    @Override
    public Page<BookResponseDTO> searchBooks(String author, Integer year, Pageable pageable) {
        Page<Book> books;

        if (author != null && year != null) {
            books = bookRepository
                    .findByAuthorContainingIgnoreCaseAndPublishedYearAndAvailableCopiesGreaterThan(
                            author, year, 0, pageable);
        } else if (author != null) {
            books = bookRepository
                    .findByAuthorContainingIgnoreCaseAndAvailableCopiesGreaterThan(
                            author, 0, pageable);
        } else if (year != null) {
            books = bookRepository
                    .findByPublishedYearAndAvailableCopiesGreaterThan(
                            year, 0, pageable);
        } else {
            books = bookRepository.findByAvailableCopiesGreaterThan(0, pageable);
        }

        return books.map(book -> modelMapper.map(book, BookResponseDTO.class));
    }


    @Override
    public Page<BookResponseDTO> searchAllBooks(String author, Integer year, Pageable pageable) {
        Page<Book> books;

        if (author != null && year != null) {
            books = bookRepository.findByAuthorContainingIgnoreCaseAndPublishedYear(author, year, pageable);
        } else if (author != null) {
            books = bookRepository.findByAuthorContainingIgnoreCase(author, pageable);
        } else if (year != null) {
            books = bookRepository.findByPublishedYear(year, pageable);
        } else {
            books = bookRepository.findAll(pageable);
        }

        return books.map(book -> modelMapper.map(book, BookResponseDTO.class));
    }

}