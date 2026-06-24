package com.digital_journal_app.backend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntryResponseDTO {
    private Long journalEntryId;
    private String title;
    private String content;
    private String categoryName;
    private String difficultyLevelName;
    private Boolean isImportant;
    private Boolean isFavourite;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long categoryId;
    private Long difficultyLevelId;
}
