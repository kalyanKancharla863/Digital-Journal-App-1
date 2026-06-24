package com.digital_journal_app.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "difficulty_levels")
public class DifficultyLevel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long difficultyLevelId;

    private String difficultyLevelName;

    @OneToMany(mappedBy = "difficultyLevel")
    private List<JournalEntry> journalEntriesList;

}
