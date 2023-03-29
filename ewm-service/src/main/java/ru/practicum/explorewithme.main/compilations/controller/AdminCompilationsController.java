package ru.practicum.explorewithme.main.compilations.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.compilations.CompilationsService;
import ru.practicum.explorewithme.main.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.NewCompilationDto;
import ru.practicum.explorewithme.main.compilations.dto.UpdateCompilationDto;

import javax.validation.Valid;

/**
 *
 */
@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@Validated
public class AdminCompilationsController {
    private final CompilationsService compilationsService;

    @Autowired
    public AdminCompilationsController(CompilationsService compilationsService) {
        this.compilationsService = compilationsService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Запрос на добавление новой подборки {}", newCompilationDto.getTitle());
        return compilationsService.create(newCompilationDto);
    }

    @DeleteMapping("{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") int compilationId) {
        log.info("Запрос на удаление подборки по id: {}", compilationId);
        compilationsService.deleteCompilation(compilationId);
    }

    @PatchMapping("{compId}")
    public CompilationDto updateCompilation(@PathVariable("compId") int compilationId,
                                            @Valid @RequestBody UpdateCompilationDto updateCompilationDto) {
        log.info("Запрос на обновление подборки по id: {}", compilationId);
        return compilationsService.updateCompilation(compilationId, updateCompilationDto);
    }
}
