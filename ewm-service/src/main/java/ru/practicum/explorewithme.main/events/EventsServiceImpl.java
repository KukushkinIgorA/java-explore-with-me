package ru.practicum.explorewithme.main.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.practicum.explorewithme.main.categories.CategoriesService;
import ru.practicum.explorewithme.main.categories.model.Category;
import ru.practicum.explorewithme.main.dictionary.EventSort;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.dictionary.ParticipationRequestStatus;
import ru.practicum.explorewithme.main.events.dto.*;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.events.model.Location;
import ru.practicum.explorewithme.main.events.repository.EventsRepository;
import ru.practicum.explorewithme.main.events.repository.LocationRepository;
import ru.practicum.explorewithme.main.exception.ConflictException;
import ru.practicum.explorewithme.main.exception.NotFoundException;
import ru.practicum.explorewithme.main.requests.ParticipationRequestsMapper;
import ru.practicum.explorewithme.main.requests.ParticipationRequestsRepository;
import ru.practicum.explorewithme.main.requests.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.main.requests.model.ParticipationRequest;
import ru.practicum.explorewithme.main.stats.StatsService;
import ru.practicum.explorewithme.main.users.UsersService;
import ru.practicum.explorewithme.main.users.model.User;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventsServiceImpl implements EventsService {

    private final EventsRepository eventsRepository;
    private final LocationRepository locationRepository;
    private final UsersService usersService;
    private final CategoriesService categoriesService;
    private final ParticipationRequestsRepository participationRequestsRepository;
    private final StatsService statsService;

    @Override
    public List<EventShortDto> getUserEvents(int userId, int from, int size) {
        usersService.getValidUser(userId);
        List<Event> events = eventsRepository.findEventByInitiator_Id(userId, PageRequest.of(from / size, size));
        Map<Integer, Integer> eventViews = statsService.getEventViews(events);
        return events.stream().map(event -> EventsMapper.toEventShortDto(event, eventViews)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto create(int userId, NewEventDto newEventDto) {
        User user = usersService.getValidUser(userId);
        Category category = categoriesService.getValidCategory(newEventDto.getCategory());
        validate(newEventDto.getEventDate());
        Location location = locationRepository.findLocationByLatAndLon(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon());
        if (location == null) {
            location = locationRepository.save(LocationMapper.toLocation(newEventDto.getLocation()));
        }
        Event event = eventsRepository.save(EventsMapper.toEvent(newEventDto, category, user, location));
        Map<Integer, Integer> eventViews = statsService.getEventViews(List.of(event));
        return EventsMapper.toEventFullDto(event, eventViews);
    }

    @Override
    public EventFullDto getUserEvent(int userId, int eventId) {
        Event event = getValidEvent(eventId, userId);
        Map<Integer, Integer> eventViews = statsService.getEventViews(List.of(event));
        return EventsMapper.toEventFullDto(event, eventViews);
    }

    @Override
    public EventFullDto getPublicEvent(int eventId, HttpServletRequest httpServletRequest) {
        Event event = getValidEvent(eventId);
        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new NotFoundException(String.format("на сервере отстутствует событие c id = %s", eventId));
        }
        statsService.addHit(httpServletRequest);
        Map<Integer, Integer> eventViews = statsService.getEventViews(List.of(event));
        return EventsMapper.toEventFullDto(event, eventViews);
    }

    @Override
    @Transactional
    public EventFullDto updateUserEvent(int userId, int eventId, UpdateEventUserRequestDto updateEventUserRequestDto) {
        Event event = getValidEvent(eventId, userId);
        if (event.getState().equals(EventStatus.PUBLISHED)) {
            throw new ConflictException(String.format("нельзя редактировать события в статусе %s",
                    EventStatus.PUBLISHED));
        }
        Category category = null;
        Location location = null;
        if (updateEventUserRequestDto.getEventDate() != null) {
            validate(updateEventUserRequestDto.getEventDate());
        }
        if (updateEventUserRequestDto.getCategory() != null) {
            category = categoriesService.getValidCategory(updateEventUserRequestDto.getCategory());
        }
        if (updateEventUserRequestDto.getLocation() != null) {
            location = locationRepository.findLocationByLatAndLon(updateEventUserRequestDto.getLocation().getLat(), updateEventUserRequestDto.getLocation().getLon());
            if (location == null) {
                location = locationRepository.save(LocationMapper.toLocation(updateEventUserRequestDto.getLocation()));
            }
        }
        EventsMapper.toUpdateUserEvent(updateEventUserRequestDto, category, event, location);
        Map<Integer, Integer> eventViews = statsService.getEventViews(List.of(event));
        return EventsMapper.toEventFullDto(event, eventViews);
    }

    @Override
    @Transactional
    public EventFullDto updateAdminEvent(int eventId, UpdateEventUserRequestDto updateEventUserRequestDto) {
        Event event = getValidEvent(eventId);
        if (!event.getState().equals(EventStatus.PENDING)) {
            throw new ConflictException(String.format("нельзя публиковать события в статусе отличном от %s", EventStatus.PENDING));
        }
        Category category = null;
        Location location = null;
        if (updateEventUserRequestDto.getEventDate() != null) {
            validate(updateEventUserRequestDto.getEventDate());
        }
        if (updateEventUserRequestDto.getCategory() != null) {
            category = categoriesService.getValidCategory(updateEventUserRequestDto.getCategory());
        }
        if (updateEventUserRequestDto.getLocation() != null) {
            location = locationRepository.save(LocationMapper.toLocation(updateEventUserRequestDto.getLocation()));
        }
        EventsMapper.toUpdateAdminEvent(updateEventUserRequestDto, category, event, location);
        Map<Integer, Integer> eventViews = statsService.getEventViews(List.of(event));
        return EventsMapper.toEventFullDto(event, eventViews);
    }


    @Override
    public List<EventShortDto> getEventsPublicFilter(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, EventSort eventSort, int from, int size, HttpServletRequest httpServletRequest) {
        List<Event> events = eventsRepository.findEventsPublicFilter(text, categories, paid, rangeStart, rangeEnd, eventSort, from, size);
        if (onlyAvailable != null && onlyAvailable.equals(true)) {
            events = events.stream().filter(event -> (event.getParticipantLimit() == 0 || event.getConfirmedRequests().size() < event.getParticipantLimit())).collect(Collectors.toList());
        }
        statsService.addHit(httpServletRequest);
        Map<Integer, Integer> eventViews = statsService.getEventViews(events);
        return events.stream().map(event -> EventsMapper.toEventShortDto(event, eventViews)).collect(Collectors.toList());
    }

    @Override
    public List<EventFullDto> getEventsAdminFilter(List<Integer> users, List<EventStatus> eventStatuses, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size) {
        List<Event> events = eventsRepository.findEventsAdminFilter(users, eventStatuses, categories, rangeStart, rangeEnd, from, size);
        Map<Integer, Integer> eventViews = statsService.getEventViews(events);
        return events.stream().map(event -> EventsMapper.toEventFullDto(event, eventViews)).collect(Collectors.toList());
    }


    @Override
    public Event getValidEvent(int eventId) {
        return eventsRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("на сервере отстутствует событие c id = %s", eventId)));
    }

    @Override
    public List<ParticipationRequestDto> getEventParticipationRequest(int userId, int eventId) {
        Event event = getValidEvent(eventId, userId);
        return event.getParticipationRequests()
                .stream().map(ParticipationRequestsMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult changeEventParticipationRequestStatus(int userId, int eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        Event event = getValidEvent(eventId, userId);
        boolean requestModeration = event.isRequestModeration();
        int confirmedRequests = event.getConfirmedRequests().size();
        int participantLimit = event.getParticipantLimit();
        List<ParticipationRequest> participationRequests = participationRequestsRepository.findParticipationRequestByEvent_IdAndState(eventId, ParticipationRequestStatus.PENDING);
        List<ParticipationRequest> participationRequestsConfirmed = new ArrayList<>();
        List<ParticipationRequest> participationRequestsRejected = new ArrayList<>();
        if (eventRequestStatusUpdateRequest.getStatus().equals(ParticipationRequestStatus.CONFIRMED) && requestModeration) {
            for (ParticipationRequest participationRequest : participationRequests) {
                if (confirmedRequests < participantLimit || participantLimit == 0) {
                    participationRequest.setState(ParticipationRequestStatus.CONFIRMED);
                    participationRequestsConfirmed.add(participationRequest);
                    confirmedRequests++;
                } else {
                    participationRequest.setState(ParticipationRequestStatus.REJECTED);
                    participationRequestsRejected.add(participationRequest);
                }

            }
        } else if (eventRequestStatusUpdateRequest.getStatus().equals(ParticipationRequestStatus.REJECTED)) {
            for (ParticipationRequest participationRequest : participationRequests) {
                participationRequest.setState(ParticipationRequestStatus.REJECTED);
                participationRequestsRejected.add(participationRequest);
            }
        }
        if (CollectionUtils.isEmpty(participationRequestsConfirmed) && CollectionUtils.isEmpty(participationRequestsRejected)) {
            throw new ConflictException(String.format("Лимит участников достиг предела %s", participantLimit));
        }
        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(participationRequestsConfirmed.stream().map(ParticipationRequestsMapper::toParticipationRequestDto).collect(Collectors.toList()))
                .rejectedRequests(participationRequestsRejected.stream().map(ParticipationRequestsMapper::toParticipationRequestDto).collect(Collectors.toList()))
                .build();
    }

    private Event getValidEvent(int eventId, int userId) {
        return eventsRepository.findEventByIdAndInitiator_Id(eventId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("на сервере отстутствует событие c id = %s и userId = %s", eventId, userId)));
    }

    private void validate(LocalDateTime eventDate) {
        LocalDateTime now = LocalDateTime.now();
        if (eventDate.isBefore(now.plusHours(2))) {
            throw new ConflictException(String.format("время создания раньше чем через 2 часа от текущего времени %s", now));
        }
    }
}
