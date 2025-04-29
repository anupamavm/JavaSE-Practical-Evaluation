package com.library.online_library.controller;

import com.library.online_library.dto.book.*;
import com.library.online_library.service.BookService;
import com.library.online_library.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody @Valid BookRequestDTO dto, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !userService.isAuthenticateUser(loggedInUserId )){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        BookResponseDTO createdBook = bookService.addBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @GetMapping("/available")
    public ResponseEntity<Page<BookResponseDTO>> getAvailableBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BookResponseDTO> books = bookService.getAvailableBooks(PageRequest.of(page, size));
        return ResponseEntity.ok(books);
    }


    @GetMapping("/search")
    public ResponseEntity<Page<BookResponseDTO>> searchBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<BookResponseDTO> results = bookService.searchBooks(author, year, PageRequest.of(page, size));
        return ResponseEntity.ok(results);
    }

    @GetMapping("/searchall")
    public ResponseEntity<?> searchBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size ,
            HttpSession session
    ) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !userService.isAuthenticateUser(loggedInUserId)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        Page<BookResponseDTO> results = bookService.searchBooks(author, year, PageRequest.of(page, size));
        return ResponseEntity.ok(results);
    }

}