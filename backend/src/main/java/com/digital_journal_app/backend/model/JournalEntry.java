package com.digital_journal_app.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "journal_entries")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journalEntryId;

    private String title;

    private String content;

    private LocalDateTime createdTime;
    private LocalDateTime updateTime;

    private Boolean isImportant;
    private Boolean isFavourite;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "difficulty_level_id")
    private DifficultyLevel difficultyLevel;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void prePersist(){
        this.createdTime=LocalDateTime.now();
        this.updateTime=LocalDateTime.now();
    }

    @PreUpdate
    public  void preUpdate(){
        this.updateTime=LocalDateTime.now();
    }

}
