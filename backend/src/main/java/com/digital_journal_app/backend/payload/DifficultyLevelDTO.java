package com.digital_journal_app.backend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DifficultyLevelDTO {
   private Long difficultyLevelId;

    private String difficultyLevelName;
    
}
