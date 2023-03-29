package ru.practicum.explorewithme.main.compilations.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.compilations.CompilationsService;
import ru.practicum.explorewithme.main.compilations.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_PAGE_SIZE;
import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_START_INDEX;

/**
 *
 */
@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@Validated
public class PublicCompilationsController {
    private final CompilationsService compilationsService;

    @Autowired
    public PublicCompilationsController(CompilationsService compilationsService) {
        this.compilationsService = compilationsService;
    }

    @GetMapping()
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                @PositiveOrZero @RequestParam(name = "from",
                                                        required = false, defaultValue = DEFAULT_START_INDEX) int from,
                                                @Positive @RequestParam(name = "size",
                                                        required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.info("Запрос на поиск подборок подборок событий по параметрам");
        return compilationsService.getCompilations(pinned, from, size);
    }

    @GetMapping("{compId}")
    public CompilationDto getCompilation(@PathVariable("compId") int compilationId) {
        log.info("Запрос на поиск подборки по id: {}", compilationId);
        return compilationsService.getCompilation(compilationId);
    }

}
