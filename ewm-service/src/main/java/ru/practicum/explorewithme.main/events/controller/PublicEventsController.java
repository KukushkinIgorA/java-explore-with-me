package ru.practicum.explorewithme.main.events.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.dictionary.EventSort;
import ru.practicum.explorewithme.main.events.EventsService;
import ru.practicum.explorewithme.main.events.dto.EventFullDto;
import ru.practicum.explorewithme.main.events.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_PAGE_SIZE;
import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_START_INDEX;

/**
 *
 */
@RestController
@RequestMapping(path = "/events")
@Slf4j
@Validated
public class PublicEventsController {
    private final EventsService eventsService;

    @Autowired
    public PublicEventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @GetMapping()
    public List<EventShortDto> getEventsPublicFilter(@RequestParam(name = "text", required = false) String text,
                                                     @RequestParam(name = "categories", required = false) List<Integer> categories,
                                                     @RequestParam(name = "paid", required = false) Boolean paid,
                                                     @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                     @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                     @RequestParam(name = "onlyAvailable", required = false) Boolean onlyAvailable,
                                                     @RequestParam(name = "sort", defaultValue = "EVENT_DATE") String eventSortString,
                                                     @PositiveOrZero @RequestParam(name = "from",
                                                             defaultValue = DEFAULT_START_INDEX) int from,
                                                     @Positive @RequestParam(name = "size",
                                                             defaultValue = DEFAULT_PAGE_SIZE) int size,
                                                     HttpServletRequest httpServletRequest) {
        log.info("Запрос событий по публичному фильтру");
        EventSort eventSort = checkEventSort(eventSortString);
        return eventsService.getEventsPublicFilter(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, eventSort, from, size, httpServletRequest);
    }

    @GetMapping("{id}")
    public EventFullDto getUserEvent(@PathVariable("id") int eventId,
                                     HttpServletRequest httpServletRequest) {
        log.info("Запрос опубликованного события {}", eventId);
        return eventsService.getPublicEvent(eventId, httpServletRequest);
    }

    private static EventSort checkEventSort(String eventSortString) {
        return EventSort.from(eventSortString).orElseThrow(() ->
                new IllegalArgumentException("Unknown state: " + eventSortString));
    }
}
