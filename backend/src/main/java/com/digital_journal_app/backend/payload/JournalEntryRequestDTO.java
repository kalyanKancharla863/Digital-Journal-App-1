package com.digital_journal_app.backend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalEntryRequestDTO {
    private String title;
    private String content;
    private Long categoryId;
    private Long userId;
    private Long difficultyLevelId;
    private Boolean isImportant;
    private Boolean isFavourite;
    
}
