package com.tuananhdo.service.impl;

import com.tuananhdo.entity.Category;
import com.tuananhdo.exception.ResourceNotFoundException;
import com.tuananhdo.mapper.CategoryMapper;
import com.tuananhdo.payload.CategoryDTO;
import com.tuananhdo.repository.CategoryRepository;
import com.tuananhdo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.mapToCategoryEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToCategoryDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        category.setId(categoryId);
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.mapToCategoryDTO(updatedCategory);
    }

    @Override
    public void deleteCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDTO getCategoryById(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        return categoryMapper.mapToCategoryDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::mapToCategoryDTO)
                .collect(Collectors.toList());
    }
}
