package ru.practicum.explorewithme.main.stats;

import ru.practicum.explorewithme.dto.StatDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {

    void addHit(HttpServletRequest request);

    List<StatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
