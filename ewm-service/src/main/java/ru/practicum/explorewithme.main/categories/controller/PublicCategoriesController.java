package ru.practicum.explorewithme.main.categories.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.categories.CategoriesService;
import ru.practicum.explorewithme.main.categories.dto.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_PAGE_SIZE;
import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_START_INDEX;

/**
 *
 */
@RestController
@RequestMapping(path = "/categories")
@Slf4j
@Validated
public class PublicCategoriesController {
    private final CategoriesService categoriesService;

    @Autowired
    public PublicCategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping()
    public List<CategoryDto> getCategories(@PositiveOrZero @RequestParam(name = "from",
            required = false, defaultValue = DEFAULT_START_INDEX) int from,
                                           @Positive @RequestParam(name = "size",
                                                   required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.info("Запрос категорий");
        return categoriesService.getCategories(from, size);
    }

    @GetMapping("{catId}")
    public CategoryDto getCategory(@PathVariable("catId") int categoryId) {
        log.info("Запрос категории по id: {}", categoryId);
        return categoriesService.getCategory(categoryId);
    }
}
