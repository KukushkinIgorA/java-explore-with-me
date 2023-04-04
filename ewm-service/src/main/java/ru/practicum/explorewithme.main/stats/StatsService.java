package ru.practicum.explorewithme.main.stats;

import ru.practicum.explorewithme.dto.StatDto;
import ru.practicum.explorewithme.main.events.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatsService {

    void addHit(HttpServletRequest request);

    List<StatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);


    Map<Integer, Integer> getEventViews(List<Event> events);
}
