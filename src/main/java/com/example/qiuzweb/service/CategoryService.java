package com.example.qiuzweb.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.qiuzweb.domain.Category;
import com.example.qiuzweb.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
    public List<Category> allCategory() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long id) {
        return categoryRepository.findByCategoryId(id);
    }
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
}
