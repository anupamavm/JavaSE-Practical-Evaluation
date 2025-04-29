package com.library.online_library.dto.book;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BookResponseDTO {
    private Long id;
    private String title;
    private String author;
    private Integer publishedYear;
    private Integer availableCopies;
}