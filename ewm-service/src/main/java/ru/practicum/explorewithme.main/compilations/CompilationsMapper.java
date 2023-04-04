package ru.practicum.explorewithme.main.compilations;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.NewCompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.UpdateCompilationDto;
import ru.practicum.explorewithme.main.compilations.model.Compilation;
import ru.practicum.explorewithme.main.events.EventsMapper;
import ru.practicum.explorewithme.main.events.model.Event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationsMapper {
    public static CompilationDto toCompilationDto(Compilation compilation, List<Event> events, Map<Integer, Integer> eventViews) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .events(events.stream().map(event -> EventsMapper.toEventShortDto(event, eventViews)).collect(Collectors.toList()))
                .build();
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return Compilation.builder()
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .events(events)
                .build();
    }

    public static void updateCompilation(UpdateCompilationDto updateCompilationDto, Compilation compilation) {
        compilation.setPinned(updateCompilationDto.isPinned() ? updateCompilationDto.isPinned() : compilation.isPinned());
        compilation.setTitle(updateCompilationDto.getTitle() != null && !updateCompilationDto.getTitle().isBlank()
                ? updateCompilationDto.getTitle() : compilation.getTitle());
    }
}
