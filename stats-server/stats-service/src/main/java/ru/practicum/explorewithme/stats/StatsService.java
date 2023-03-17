package ru.practicum.explorewithme.stats;

import ru.practicum.explorewithme.dto.stats.HitDto;
import ru.practicum.explorewithme.dto.stats.StatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void create(HitDto hitDto);

    List<StatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
