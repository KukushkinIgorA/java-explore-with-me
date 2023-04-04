package ru.practicum.explorewithme.main.stats;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.dto.StatDto;
import ru.practicum.explorewithme.main.events.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.client.StatsUtils.DT_FORMATTER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final StatsClient statsClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value(value = "${app.name}")
    private String appName;

    @Override
    public void addHit(HttpServletRequest httpServletRequest) {
        log.info("Сохранение статистики httpServletRequest = {}", httpServletRequest);

        statsClient.addHit(appName, httpServletRequest.getRequestURI(), httpServletRequest.getRemoteAddr(),
                LocalDateTime.parse(LocalDateTime.now().format(DT_FORMATTER), DT_FORMATTER));
    }

    @Override
    public List<StatDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Получение статистики start = {}, end = {}, uris = {}, unique = {}", start, end, uris, unique);

        ResponseEntity<Object> response = statsClient.getStats(start, end, uris, unique);

        try {
            return Arrays.asList(objectMapper.readValue(objectMapper.writeValueAsString(response.getBody()), StatDto[].class));
        } catch (IOException exception) {
            throw new ClassCastException(exception.getMessage());
        }
    }

    @Override
    public Map<Integer, Integer> getEventViews(List<Event> events) {

        Map<Integer, Integer> eventViews = new HashMap<>();

        if (events.isEmpty()) {
            return eventViews;
        }

        List<String> uris = events.stream()
                .map(Event::getId)
                .map(id -> ("/events/" + id))
                .collect(Collectors.toList());

        List<StatDto> stats = getStats(LocalDateTime.of(1970, 1, 1, 0, 0, 0), LocalDateTime.now(), uris, null);
        stats.forEach(stat -> {
            int eventId = Integer.parseInt(stat.getUri()
                    .split("/", 0)[2]);
            eventViews.put(eventId, (int) (eventViews.getOrDefault(eventId, 0) + stat.getHits()));
        });
        return eventViews;
    }
}
