package ru.practicum.explorewithme.main.categories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.categories.model.Category;

public interface CategoriesRepository extends JpaRepository<Category, Integer> {
}
