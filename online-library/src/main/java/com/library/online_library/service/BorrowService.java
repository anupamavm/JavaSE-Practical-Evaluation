package com.library.online_library.service;

import com.library.online_library.dto.borrow.BorrowRequestDTO;
import com.library.online_library.dto.borrow.BorrowResponseDTO;
import java.util.List;

public interface BorrowService {
    BorrowResponseDTO borrowBook(BorrowRequestDTO dto);
    BorrowResponseDTO returnBook(Long borrowId);
    List<BorrowResponseDTO> getBorrowHistory(Long userId);
}
