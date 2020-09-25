package com.sherwin.ebook.service;

import com.sherwin.ebook.domain.Category;
import com.sherwin.ebook.domain.Role;
import com.sherwin.ebook.repository.CategoryRepository;
import com.sherwin.ebook.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void save(Category category){
        categoryRepository.save(category);
    }

    public Category getCategoryByName(String name){
        return categoryRepository.findByName(name);
    }
    public List<Category> getCategory(){
        return categoryRepository.findAll();
    }
}
