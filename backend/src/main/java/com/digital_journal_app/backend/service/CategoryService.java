package com.digital_journal_app.backend.service;

import com.digital_journal_app.backend.payload.CategoryDTO;
import com.digital_journal_app.backend.payload.DifficultyLevelDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> getAllCategories();
    List<DifficultyLevelDTO> getAllDifficultyLevels();
}
