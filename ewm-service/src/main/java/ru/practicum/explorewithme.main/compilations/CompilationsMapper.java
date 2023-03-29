package ru.practicum.explorewithme.main.compilations;

import ru.practicum.explorewithme.main.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.NewCompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.UpdateCompilationDto;
import ru.practicum.explorewithme.main.compilations.model.Compilation;
import ru.practicum.explorewithme.main.events.EventsMapper;
import ru.practicum.explorewithme.main.events.model.Event;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationsMapper {
    public static CompilationDto toCompilationDto(Compilation compilation, List<Event> events) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.getPinned())
                .title(compilation.getTitle())
                .events(events.stream().map(EventsMapper::toEventShortDto).collect(Collectors.toList()))
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto) {
        return Compilation.builder()
                .pinned(newCompilationDto.getPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public static void updateCompilation(UpdateCompilationDto updateCompilationDto, Compilation compilation) {
        compilation.setPinned(updateCompilationDto.getPinned() != null ? updateCompilationDto.getPinned() : compilation.getPinned());
        compilation.setTitle(updateCompilationDto.getTitle() != null ? updateCompilationDto.getTitle() : compilation.getTitle());
    }
}
