package com.digital_journal_app.backend.controller;

import com.digital_journal_app.backend.payload.CategoryDTO;
import com.digital_journal_app.backend.payload.DifficultyLevelDTO;
import com.digital_journal_app.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(
        originPatterns = "*",
        allowCredentials = "true"
)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }


            @GetMapping("/difficultylevels")
    public ResponseEntity<List<DifficultyLevelDTO>> getAllDifficultyLevels() {
        List<DifficultyLevelDTO> difficultyLevels = categoryService.getAllDifficultyLevels();
        return new ResponseEntity<>(difficultyLevels, HttpStatus.OK);
    }
    
    
}
