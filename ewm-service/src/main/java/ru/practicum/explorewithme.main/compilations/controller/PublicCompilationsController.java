package ru.practicum.explorewithme.main.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class PublicCompilationsController {
    private final CompilationsService compilationsService;

    @GetMapping()
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) boolean pinned,
                                                @PositiveOrZero @RequestParam(name = "from",
                                                        defaultValue = DEFAULT_START_INDEX) int from,
                                                @Positive @RequestParam(name = "size",
                                                        defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.info("Запрос на поиск подборок подборок событий по параметрам");
        return compilationsService.getCompilations(pinned, from, size);
    }

    @GetMapping("{compId}")
    public CompilationDto getCompilation(@PathVariable("compId") int compilationId) {
        log.info("Запрос на поиск подборки по id: {}", compilationId);
        return compilationsService.getCompilation(compilationId);
    }

}
