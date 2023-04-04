package ru.practicum.explorewithme.main.events;

import ru.practicum.explorewithme.main.dictionary.EventSort;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.events.dto.*;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.requests.dto.ParticipationRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventsService {

    List<EventShortDto> getUserEvents(int userId, int from, int size);

    EventFullDto create(int userId, NewEventDto newEventDto);

    EventFullDto getUserEvent(int userId, int eventId);

    EventFullDto updateUserEvent(int userId, int eventId, UpdateEventUserRequestDto updateEventUserRequestDto);

    List<EventShortDto> getEventsPublicFilter(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSort eventSort, int from, int size, HttpServletRequest httpServletRequest);

    List<EventFullDto> getEventsAdminFilter(List<Integer> users, List<EventStatus> eventStatuses, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto updateAdminEvent(int eventId, UpdateEventUserRequestDto updateEventUserRequestDto);

    EventFullDto getPublicEvent(int eventId, HttpServletRequest httpServletRequest);

    Event getValidEvent(int eventId);

    List<ParticipationRequestDto> getEventParticipationRequest(int userId, int eventId);

    EventRequestStatusUpdateResult changeEventParticipationRequestStatus(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
