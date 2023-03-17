package ru.practicum.explorewithme.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explorewithme.dto.stats.StatDto;
import ru.practicum.explorewithme.stats.model.Hit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Integer> {
    @Query("select new ru.practicum.explorewithme.dto.stats.StatDto (hit.app, hit.uri, count(hit.uri)) from Hit hit " +
            "where hit.timestamp >= :start " +
            "and hit.timestamp < :end " +
            "group by hit.app, hit.uri " +
            "order by count(hit.uri) desc")
    List<StatDto> findStatDto(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.explorewithme.dto.stats.StatDto (hit.app, hit.uri, count(distinct hit.uri)) from Hit hit " +
            "where hit.timestamp >= :start " +
            "and hit.timestamp < :end " +
            "group by hit.app, hit.uri " +
            "order by count(hit.uri) desc")
    List<StatDto> findStatDtoDistinct(LocalDateTime start, LocalDateTime end);
    @Query("select new ru.practicum.explorewithme.dto.stats.StatDto (hit.app, hit.uri, count(hit.uri)) from Hit hit " +
            "where hit.uri in (:uris) " +
            "and hit.timestamp >= :start " +
            "and hit.timestamp < :end " +
            "group by hit.app, hit.uri " +
            "order by count(hit.uri) desc")
    List<StatDto> findStatDtoByUriIs(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.explorewithme.dto.stats.StatDto (hit.app, hit.uri, count(distinct hit.uri)) from Hit hit " +
            "where hit.uri in (:uris) " +
            "and hit.timestamp >= :start " +
            "and hit.timestamp < :end " +
            "group by hit.app, hit.uri " +
            "order by count(hit.uri) desc")
    List<StatDto> findStatDtoByUriIsDistinct(LocalDateTime start, LocalDateTime end, List<String> uris);
}
