package com.tuananhdo.mapper;

import com.tuananhdo.entity.Category;
import com.tuananhdo.payload.CategoryDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {

    private final ModelMapper mapper;

    public CategoryDTO mapToCategoryDTO(Category category) {
        return mapper.map(category, CategoryDTO.class);
    }

    public Category mapToCategoryEntity(CategoryDTO categoryDTO) {
        return mapper.map(categoryDTO, Category.class);
    }
}
