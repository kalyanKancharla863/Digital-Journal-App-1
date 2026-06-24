package com.digital_journal_app.backend.service;

import com.digital_journal_app.backend.exceptions.APIException;
import com.digital_journal_app.backend.model.Category;
import com.digital_journal_app.backend.model.DifficultyLevel;
import com.digital_journal_app.backend.payload.CategoryDTO;
import com.digital_journal_app.backend.payload.DifficultyLevelDTO;
import com.digital_journal_app.backend.repositories.CategoryRepository;
import com.digital_journal_app.backend.repositories.DifficultyLevelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    ModelMapper modelMapper;
    DifficultyLevelRepository difficultyLevelRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper,DifficultyLevelRepository difficultyLevelRepository) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.difficultyLevelRepository=difficultyLevelRepository;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        if(categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new APIException( "Category with name '" + category.getCategoryName() + "' already exists.");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DifficultyLevelDTO> getAllDifficultyLevels() {
        List<DifficultyLevel> difficultyLevels = difficultyLevelRepository.findAll();
        return difficultyLevels.stream()
                .map(difficultyLevel -> modelMapper.map(difficultyLevel, DifficultyLevelDTO.class))
                .collect(Collectors.toList());
    }
}
