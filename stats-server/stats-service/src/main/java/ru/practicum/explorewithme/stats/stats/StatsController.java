package ru.practicum.explorewithme.stats.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatDto;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class StatsController {
    private final StatsService statsService;

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody HitDto hitDto) {
        log.info("Запрос на сохранение статистики вызова сервиса по uri {}", hitDto.getUri());
        statsService.create(hitDto);
    }

    @GetMapping("/stats")
    public List<StatDto> getStats(@RequestParam(name = "start") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                  @RequestParam(name = "end") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                  @RequestParam(name = "uris", required = false) List<String> uris,
                                  @RequestParam(name = "unique", required = false) boolean unique) {
        log.info("Запрос статистики по uris {}", uris);
        return statsService.getStats(start, end, uris, unique);
    }
}
