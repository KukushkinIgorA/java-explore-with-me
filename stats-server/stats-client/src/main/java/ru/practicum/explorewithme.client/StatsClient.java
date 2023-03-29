package ru.practicum.explorewithme.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.explorewithme.dto.HitDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static ru.practicum.explorewithme.client.StatsUtils.*;

@Service
@Slf4j
public class StatsClient extends BaseClient {
    @Autowired
    public StatsClient(RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(STATS_URL))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> addHit(String appName, String uri, String ip, LocalDateTime timestamp) {
        log.info("Сохранение в статистику с параметрами appName = {}, uri = {}, ip = {}, timestamp = {}",
                appName, uri, ip, timestamp);

        HitDto hitDto = HitDto.builder()
                .app(appName)
                .uri(uri)
                .ip(ip)
                .timestamp(timestamp)
                .build();
        return post(HIT_ENDPOINT, hitDto);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris) {
        return getStats(start, end, uris, null);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end) {
        return getStats(start, end, null, null);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, Boolean unique) {
        return getStats(start, end, null, unique);
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        log.info("Получение статистики по параметрам start = {}, end = {}, uris = {}, unique = {}",
                start, end, uris, unique);

        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException("Недопустимый временной промежуток.");
        }

        StringBuilder uriBuilder = new StringBuilder(STATS_ENDPOINT + "?start={start}&end={end}");
        Map<String, Object> parameters = Map.of(
                "start", start.format(DT_FORMATTER),
                "end", end.format(DT_FORMATTER)
        );

        if (uris != null && !uris.isEmpty()) {
            for (String uri : uris) {
                uriBuilder.append("&uris=").append(uri);
            }
        }
        if (unique != null) {
            uriBuilder.append("&unique=").append(unique);
        }

        return get(uriBuilder.toString(), parameters);
    }
}
