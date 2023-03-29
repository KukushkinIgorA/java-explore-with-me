package ru.practicum.explorewithme.main.compilations;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.compilations.model.Compilation;

import java.util.List;

public interface CompilationsRepository extends JpaRepository<Compilation, Integer> {
    List<Compilation> findCompilationByPinned(Boolean pinned, Pageable pageable);
}
