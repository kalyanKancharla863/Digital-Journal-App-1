package com.digital_journal_app.backend.repositories;

import com.digital_journal_app.backend.model.DifficultyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel, Long> {
    boolean existsByDifficultyLevelName(String difficultyLevelName);
}