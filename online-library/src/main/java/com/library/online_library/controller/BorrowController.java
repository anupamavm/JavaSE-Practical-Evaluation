package com.library.online_library.controller;

import com.library.online_library.dto.borrow.*;
import com.library.online_library.service.BorrowService;
import com.library.online_library.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@RequiredArgsConstructor
public class BorrowController {
    private final BorrowService borrowService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> borrow(@RequestBody @Valid BorrowRequestDTO dto, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !userService.isAuthenticateUser(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        dto.setUserId(loggedInUserId);
        BorrowResponseDTO response = borrowService.borrowBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<?> returnBook(@PathVariable Long id, HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !userService.isAuthenticateUser(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        BorrowResponseDTO response = borrowService.returnBook(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<?> history(HttpSession session) {
        Long loggedInUserId = (Long) session.getAttribute("userId");
        if (loggedInUserId == null || !userService.isAuthenticateUser(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        List<BorrowResponseDTO> history = borrowService.getBorrowHistory(loggedInUserId);
        return ResponseEntity.ok(history);
    }
}
