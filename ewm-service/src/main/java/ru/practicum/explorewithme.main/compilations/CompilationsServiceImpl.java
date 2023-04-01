package ru.practicum.explorewithme.main.compilations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class CompilationsServiceImpl implements CompilationsService {

    private final CompilationsRepository compilationsRepository;

    private final EventsRepository eventsRepository;

    @Autowired
    public CompilationsServiceImpl(CompilationsRepository compilationsRepository, EventsRepository eventsRepository) {
        this.compilationsRepository = compilationsRepository;
        this.eventsRepository = eventsRepository;
    }

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationsRepository.save(CompilationsMapper.toCompilation(newCompilationDto));
        List<Event> removeEvents = eventsRepository.findEventByCompilation_Id(compilation.getId());
        if (!CollectionUtils.isEmpty(removeEvents)) {
            for (Event removeEvent : removeEvents) {
                removeEvent.setCompilation(null);
            }
        }
        List<Event> events = new ArrayList<>();
        if (!CollectionUtils.isEmpty(newCompilationDto.getEvents())) {
            events = eventsRepository.findAllById(newCompilationDto.getEvents());
            for (Event event : events) {
                event.setCompilation(List.of(compilation));
            }
        }
        return CompilationsMapper.toCompilationDto(compilation, events);
    }

    @Override
    @Transactional
    public CompilationDto updateCompilation(int compilationId, UpdateCompilationDto updateCompilationDto) {
        Compilation compilation = getValidCompilation(compilationId);
        CompilationsMapper.updateCompilation(updateCompilationDto, compilation);
        List<Event> events = new ArrayList<>();
        if (!CollectionUtils.isEmpty(updateCompilationDto.getEvents())) {
            events = eventsRepository.findAllById(updateCompilationDto.getEvents());
            for (Event event : events) {
                event.setCompilation(List.of(compilation));
            }
        }
        return CompilationsMapper.toCompilationDto(compilation, events);
    }

    @Override
    public CompilationDto getCompilation(int compilationId) {
        Compilation compilation = getValidCompilation(compilationId);
        return CompilationsMapper.toCompilationDto(compilation, compilation.getEvents());
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
                .map(compilation -> CompilationsMapper.toCompilationDto(compilation, compilation.getEvents()))
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
