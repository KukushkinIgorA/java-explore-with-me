package ru.practicum.explorewithme.stats.stats;

import ru.practicum.explorewithme.dto.HitDto;
import ru.practicum.explorewithme.dto.StatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    void create(HitDto hitDto);

    List<StatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
