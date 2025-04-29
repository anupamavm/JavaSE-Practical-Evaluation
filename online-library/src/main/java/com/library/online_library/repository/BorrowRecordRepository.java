package com.library.online_library.repository;

import com.library.online_library.entity.BorrowRecord;
import com.library.online_library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    List<BorrowRecord> findByUser(User user);
}