package ru.practicum.explorewithme.main.requests;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.dictionary.ParticipationRequestStatus;
import ru.practicum.explorewithme.main.events.EventsService;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.exception.ConflictException;
import ru.practicum.explorewithme.main.exception.NotFoundException;
import ru.practicum.explorewithme.main.requests.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.main.requests.model.ParticipationRequest;
import ru.practicum.explorewithme.main.users.UsersService;
import ru.practicum.explorewithme.main.users.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ParticipationRequestsServiceImpl implements ParticipationRequestsService {

    private final ParticipationRequestsRepository participationRequestsRepository;

    private final UsersService usersService;

    private final EventsService eventsService;

    @Autowired
    public ParticipationRequestsServiceImpl(ParticipationRequestsRepository participationRequestsRepository,
                                            UsersService usersService,
                                            EventsService eventsService) {
        this.participationRequestsRepository = participationRequestsRepository;
        this.usersService = usersService;
        this.eventsService = eventsService;
    }


    @Override
    @Transactional
    public ParticipationRequestDto create(int userId, int eventId) {
        User user = usersService.getValidUser(userId);
        Event event = eventsService.getValidEvent(eventId);
        if (participationRequestsRepository.findParticipationRequestByEvent_IdAndRequester_Id(eventId, userId) != null) {
            throw new ConflictException(String.format("Запрос на участие в событии %s для пользователя %s уже существует", eventId, userId));
        }
        if (event.getInitiator().getId() == userId) {
            throw new ConflictException(String.format("Инициатор события %s не может учавствовать в своем событии %s", userId, eventId));
        }
        if (!event.getState().equals(EventStatus.PUBLISHED)) {
            throw new ConflictException(String.format("Нельзя учавствовать в неопубликованном событии %s", eventId));
        }
        if (event.getConfirmedRequests().size() == event.getParticipantLimit()) {
            throw new ConflictException(String.format("Достигнут лимит участников события %s", eventId));
        }

        return ParticipationRequestsMapper.toParticipationRequestDto(participationRequestsRepository
                .save(ParticipationRequestsMapper.toParticipationRequest(event, user)));
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(int userId) {
        return participationRequestsRepository.findParticipationRequestByRequester_Id(userId)
                .stream().map(ParticipationRequestsMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelParticipationRequest(int userId, int requestId) {
        ParticipationRequest participationRequest = getValidParticipationRequest(requestId);
        participationRequest.setState(ParticipationRequestStatus.CANCELED);
        return ParticipationRequestsMapper.toParticipationRequestDto(participationRequest);
    }

    @Override
    public ParticipationRequest getValidParticipationRequest(int requestId) {
        return participationRequestsRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("на сервере отстутствует запрос на участие c id = %s", requestId)));
    }
}
