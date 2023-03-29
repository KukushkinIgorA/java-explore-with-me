package ru.practicum.explorewithme.main.requests.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.requests.ParticipationRequestsService;
import ru.practicum.explorewithme.main.requests.dto.ParticipationRequestDto;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping(path = "/users")
@Slf4j
@Validated
public class PrivateParticipationRequestsController {
    private final ParticipationRequestsService participationRequestsService;

    @Autowired
    public PrivateParticipationRequestsController(ParticipationRequestsService participationRequestsService) {
        this.participationRequestsService = participationRequestsService;
    }

    @GetMapping("{userId}/requests")
    public List<ParticipationRequestDto> getParticipationRequests(@PathVariable("userId") int userId) {
        log.info("Запросы на участие в чужих событиях текущего пользователя {}", userId);
        return participationRequestsService.getParticipationRequests(userId);
    }

    @PostMapping("{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@PathVariable("userId") int userId,
                                          @RequestParam(name = "eventId") int eventId) {
        log.info("Запрос пользователя {} на участие в событии {}", userId, eventId);
        return participationRequestsService.create(userId, eventId);
    }

    @PatchMapping("{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable("userId") int userId,
                                                              @PathVariable("requestId") int requestId) {
        log.info("Запрос пользователя {} на отмену участие в событии", userId);
        return participationRequestsService.cancelParticipationRequest(userId, requestId);
    }
}
