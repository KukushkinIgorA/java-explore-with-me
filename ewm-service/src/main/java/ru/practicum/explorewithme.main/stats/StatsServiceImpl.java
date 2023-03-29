package ru.practicum.explorewithme.main.stats;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.client.StatsClient;
import ru.practicum.explorewithme.dto.StatDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.practicum.explorewithme.client.StatsUtils.DT_FORMATTER;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    public static final String EWM_SERVICE = "ewm-service";
    private final StatsClient statsClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void addHit(HttpServletRequest httpServletRequest) {
        log.info("Сохранение статистики httpServletRequest = {}", httpServletRequest);

        statsClient.addHit(EWM_SERVICE, httpServletRequest.getRequestURI(), httpServletRequest.getRemoteAddr(),
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
}
