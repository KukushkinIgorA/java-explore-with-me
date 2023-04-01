package ru.practicum.explorewithme.main.compilations;

import ru.practicum.explorewithme.main.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.NewCompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.UpdateCompilationDto;

import java.util.List;

public interface CompilationsService {

    CompilationDto create(NewCompilationDto compilationDto);

    void deleteCompilation(int compilationId);

    CompilationDto updateCompilation(int compilationId, UpdateCompilationDto updateCompilationDto);

    CompilationDto getCompilation(int compilationId);

    List<CompilationDto> getCompilations(boolean pinned, int from, int size);
}
