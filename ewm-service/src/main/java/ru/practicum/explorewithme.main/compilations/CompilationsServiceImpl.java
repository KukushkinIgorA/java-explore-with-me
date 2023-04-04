package ru.practicum.explorewithme.main.compilations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.practicum.explorewithme.main.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.NewCompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.UpdateCompilationDto;
import ru.practicum.explorewithme.main.compilations.model.Compilation;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.events.repository.EventsRepository;
import ru.practicum.explorewithme.main.exception.NotFoundException;
import ru.practicum.explorewithme.main.stats.StatsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationsServiceImpl implements CompilationsService {

    private final CompilationsRepository compilationsRepository;

    private final EventsRepository eventsRepository;

    private final StatsService statsService;

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        List<Event> events;
        if (newCompilationDto.getEvents() == null || newCompilationDto.getEvents().isEmpty()) {
            events = new ArrayList<>();
        } else {
            events = eventsRepository.findAllById(newCompilationDto.getEvents());
        }
        Compilation compilation = compilationsRepository.save(CompilationsMapper.toCompilation(newCompilationDto, events));
        Map<Integer, Integer> eventViews = statsService.getEventViews(events);
        return CompilationsMapper.toCompilationDto(compilation, events, eventViews);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(int compilationId, UpdateCompilationDto updateCompilationDto) {
        Compilation compilation = getValidCompilation(compilationId);
        CompilationsMapper.updateCompilation(updateCompilationDto, compilation);
        List<Event> events = new ArrayList<>();
        if (!CollectionUtils.isEmpty(updateCompilationDto.getEvents())) {
            events = eventsRepository.findAllById(updateCompilationDto.getEvents());
            compilation.setEvents(events);
        }
        Map<Integer, Integer> eventViews = statsService.getEventViews(events);
        return CompilationsMapper.toCompilationDto(compilation, events, eventViews);
    }

    @Override
    public CompilationDto getCompilation(int compilationId) {
        Compilation compilation = getValidCompilation(compilationId);
        Map<Integer, Integer> eventViews = statsService.getEventViews(compilation.getEvents());
        return CompilationsMapper.toCompilationDto(compilation, compilation.getEvents(), eventViews);
    }

    @Override
    public List<CompilationDto> getCompilations(boolean pinned, int from, int size) {
        List<Compilation> compilations;
        if (pinned) {
            compilations = compilationsRepository.findCompilationByPinned(true, PageRequest.of(from / size, size));
        } else {
            compilations = compilationsRepository.findAll(PageRequest.of(from / size, size)).toList();
        }

        return compilations.stream()
                .map(compilation -> CompilationsMapper.toCompilationDto(compilation, compilation.getEvents(), statsService.getEventViews(compilation.getEvents())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteCompilation(int compilationId) {
        getValidCompilation(compilationId);
        compilationsRepository.deleteById(compilationId);
    }


    private Compilation getValidCompilation(int compilationId) {
        return compilationsRepository.findById(compilationId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("на сервере отстутствует подборка c id = %s", compilationId)));
    }
}
