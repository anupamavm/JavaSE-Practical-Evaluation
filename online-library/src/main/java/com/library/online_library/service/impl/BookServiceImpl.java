package com.library.online_library.service.impl;

import com.library.online_library.dto.book.*;
import com.library.online_library.entity.Book;
import com.library.online_library.repository.BookRepository;
import com.library.online_library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return bookRepository.findAvailableBooks(pageable)
                .map(book -> modelMapper.map(book, BookResponseDTO.class));
    }

    @Override
    public List<BookResponseDTO> searchBooks(String author, Integer year) {
        List<Book> books = (author != null && year != null) ?
                bookRepository.findByAuthorContainingIgnoreCase(author).stream()
                        .filter(b -> b.getPublishedYear().equals(year)).toList()
                : (author != null) ?
                bookRepository.findByAuthorContainingIgnoreCase(author)
                : bookRepository.findByPublishedYear(year);

        return books.stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .collect(Collectors.toList());
    }
}