package com.digital_journal_app.backend.repositories;

import com.digital_journal_app.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String name);
}
