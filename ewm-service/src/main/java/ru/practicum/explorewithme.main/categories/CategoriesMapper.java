package ru.practicum.explorewithme.main.categories;

import ru.practicum.explorewithme.main.categories.dto.CategoryDto;
import ru.practicum.explorewithme.main.categories.model.Category;

public class CategoriesMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
