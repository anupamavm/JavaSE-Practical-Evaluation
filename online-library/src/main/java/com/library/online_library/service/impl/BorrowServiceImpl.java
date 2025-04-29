package com.library.online_library.service.impl;

import com.library.online_library.dto.borrow.*;
import com.library.online_library.entity.*;
import com.library.online_library.exception.ResourceNotFoundException;
import com.library.online_library.repository.*;
import com.library.online_library.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRecordRepository borrowRepo;
    private final UserRepository userRepo;
    private final BookRepository bookRepo;
    private final ModelMapper mapper;

    @Override
    public BorrowResponseDTO borrowBook(BorrowRequestDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Book book = bookRepo.findById(dto.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new IllegalStateException("No available copies to borrow");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        BorrowRecord record = BorrowRecord.builder()
                .user(user)
                .book(book)
                .borrowDate(LocalDateTime.now())
                .build();

        return mapper.map(borrowRepo.save(record), BorrowResponseDTO.class);
    }

    @Override
    public BorrowResponseDTO returnBook(Long borrowId) {
        BorrowRecord record = borrowRepo.findById(borrowId)
                .orElseThrow(() -> new ResourceNotFoundException("Borrow record not found"));

        record.setReturnDate(LocalDateTime.now());
        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);
        return mapper.map(borrowRepo.save(record), BorrowResponseDTO.class);
    }

    @Override
    public List<BorrowResponseDTO> getBorrowHistory(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return borrowRepo.findByUser(user).stream()
                .map(record -> mapper.map(record, BorrowResponseDTO.class))
                .collect(Collectors.toList());
    }
}