package ru.practicum.explorewithme.main.categories;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.main.categories.dto.CategoryDto;
import ru.practicum.explorewithme.main.categories.model.Category;
import ru.practicum.explorewithme.main.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    @Transactional
    public CategoryDto create(CategoryDto categoryDto) {
        return CategoriesMapper.toCategoryDto(categoriesRepository.save(CategoriesMapper.toCategory(categoryDto)));
    }

    @Override
    @Transactional
    public void deleteCategory(int categoryId) {
        getValidCategory(categoryId);
        categoriesRepository.deleteById(categoryId);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(int categoryId, CategoryDto categoryDto) {
        Category category = getValidCategory(categoryId);
        category.setName(categoryDto.getName());
        return CategoriesMapper.toCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getCategories(int from, int size) {
        return categoriesRepository.findAll(PageRequest.of(from / size, size))
                .stream().map(CategoriesMapper::toCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(int categoryId) {
        return CategoriesMapper.toCategoryDto(getValidCategory(categoryId));
    }

    @Override
    public Category getValidCategory(int categoryId) {
        return categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("на сервере отстутствует категория c id = %s", categoryId)));
    }
}
