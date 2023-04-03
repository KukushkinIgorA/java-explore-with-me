package ru.practicum.explorewithme.main.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.dictionary.EventSort;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.events.EventsService;
import ru.practicum.explorewithme.main.events.dto.EventFullDto;
import ru.practicum.explorewithme.main.events.dto.UpdateEventUserRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_PAGE_SIZE;
import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_START_INDEX;

/**
 *
 */
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
@Slf4j
public class AdminEventsController {
    private final EventsService eventsService;

    @GetMapping()
    public List<EventFullDto> getEventsPublicFilter(@RequestParam(name = "users", required = false) List<Integer> users,
                                                    @RequestParam(name = "states", required = false) List<String> states,
                                                    @RequestParam(name = "categories", required = false) List<Integer> categories,
                                                    @RequestParam(name = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                    @RequestParam(name = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                    @PositiveOrZero @RequestParam(name = "from",
                                                            defaultValue = DEFAULT_START_INDEX) int from,
                                                    @Positive @RequestParam(name = "size",
                                                            defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.info("Запрос событий по админскому фильтру");
        List<EventStatus> eventStatuses = new ArrayList<>();
        if (!CollectionUtils.isEmpty(states)) {
            for (String state : states) {
                eventStatuses.add(checkEventStatus(state));
            }
        }
        return eventsService.getEventsAdminFilter(users, eventStatuses, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("{eventId}")
    public EventFullDto getUserEvent(@PathVariable("eventId") int eventId,
                                     @Valid @RequestBody UpdateEventUserRequestDto updateEventUserRequestDto) {
        log.info("Запрос на обновление события {}", eventId);
        return eventsService.updateAdminEvent(eventId, updateEventUserRequestDto);
    }

    private static EventSort checkEventSort(String eventSortString) {
        return EventSort.from(eventSortString).orElseThrow(() ->
                new IllegalArgumentException("Unknown state: " + eventSortString));
    }

    private static EventStatus checkEventStatus(String eventStatusString) {
        return EventStatus.from(eventStatusString).orElseThrow(() ->
                new IllegalArgumentException("Unknown state: " + eventStatusString));
    }
}
