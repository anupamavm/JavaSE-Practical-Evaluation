package com.library.online_library.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotNull
    private Integer publishedYear;

    @NotNull
    private Integer availableCopies;
}