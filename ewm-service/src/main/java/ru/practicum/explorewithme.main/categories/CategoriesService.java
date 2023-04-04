package ru.practicum.explorewithme.main.categories;

import ru.practicum.explorewithme.main.categories.dto.CategoryDto;
import ru.practicum.explorewithme.main.categories.model.Category;

import java.util.List;

public interface CategoriesService {

    CategoryDto create(CategoryDto categoryDto);

    void deleteCategory(int categoryId);

    CategoryDto updateCategory(int categoryId, CategoryDto categoryDto);

    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategory(int categoryId);

    Category getValidCategory(int categoryId);
}
