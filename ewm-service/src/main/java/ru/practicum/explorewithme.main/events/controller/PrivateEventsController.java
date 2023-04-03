package ru.practicum.explorewithme.main.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.events.EventsService;
import ru.practicum.explorewithme.main.events.dto.*;
import ru.practicum.explorewithme.main.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_PAGE_SIZE;
import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_START_INDEX;

/**
 *
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventsController {
    private final EventsService eventsService;

    @GetMapping("{userId}/events")
    public List<EventShortDto> getUserEvents(@PathVariable("userId") int userId,
                                             @PositiveOrZero @RequestParam(name = "from",
                                                     defaultValue = DEFAULT_START_INDEX) int from,
                                             @Positive @RequestParam(name = "size",
                                                     defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.info("Запрос событий добавленных текущим пользователем {}", userId);
        return eventsService.getUserEvents(userId, from, size);
    }

    @PostMapping("{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@PathVariable("userId") int userId,
                               @Valid @RequestBody NewEventDto newEventDto
    ) {
        log.info("Запрос на добавление нового события с title {}", newEventDto.getTitle());
        return eventsService.create(userId, newEventDto);
    }

    @GetMapping("{userId}/events/{eventId}")
    public EventFullDto getUserEvent(@PathVariable("userId") int userId,
                                     @PathVariable("eventId") int eventId) {
        log.info("Запрос события {} текущего пользователем {}", eventId, userId);
        return eventsService.getUserEvent(userId, eventId);
    }

    @PatchMapping("{userId}/events/{eventId}")
    public EventFullDto updateUserEvent(@PathVariable("userId") int userId,
                                        @PathVariable("eventId") int eventId,
                                        @Valid @RequestBody UpdateEventUserRequestDto updateEventUserRequestDto) {
        log.info("Запрос на обновление события {} текущего пользователем {}", eventId, userId);
        return eventsService.updateUserEvent(userId, eventId, updateEventUserRequestDto);
    }

    @GetMapping("{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getEventParticipationRequest(@PathVariable("userId") int userId,
                                                                      @PathVariable("eventId") int eventId) {
        log.info("Запрос заявок на участие в событии {} текущего пользователя {}", eventId, userId);
        return eventsService.getEventParticipationRequest(userId, eventId);
    }

    @PatchMapping("{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeEventParticipationRequestStatus(@PathVariable("userId") int userId,
                                                                                @PathVariable("eventId") int eventId,
                                                                                @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("Изменение статуса заявок на участие в событии {} текущего пользователя {}", eventId, userId);
        return eventsService.changeEventParticipationRequestStatus(userId, eventId, eventRequestStatusUpdateRequest);
    }
}
