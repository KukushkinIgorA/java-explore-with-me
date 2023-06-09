package ru.practicum.explorewithme.main.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.categories.CategoriesService;
import ru.practicum.explorewithme.main.categories.dto.CategoryDto;

import javax.validation.Valid;

/**
 *
 */
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoriesController {
    private final CategoriesService categoriesService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("Запрос на добавление новой категории с name {}", categoryDto.getName());
        return categoriesService.create(categoryDto);
    }

    @DeleteMapping("{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("catId") int categoryId) {
        log.info("Запрос на удаление категории по id: {}", categoryId);
        categoriesService.deleteCategory(categoryId);
    }

    @PatchMapping("{catId}")
    public CategoryDto updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("catId") int categoryId) {
        log.info("Запрос на изменение категории по id: {}", categoryId);
        return categoriesService.updateCategory(categoryId, categoryDto);
    }
}
