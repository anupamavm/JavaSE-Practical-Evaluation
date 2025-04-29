package com.library.online_library.dto.borrow;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BorrowRequestDTO {
    @NotNull
    private Long userId;

    @NotNull
    private Long bookId;
}