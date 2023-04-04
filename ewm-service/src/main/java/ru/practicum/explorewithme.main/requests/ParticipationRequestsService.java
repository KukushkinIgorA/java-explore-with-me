package ru.practicum.explorewithme.main.requests;

import ru.practicum.explorewithme.main.requests.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.main.requests.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestsService {

    ParticipationRequestDto create(int userId, int eventId);

    List<ParticipationRequestDto> getParticipationRequests(int userId);

    ParticipationRequestDto cancelParticipationRequest(int userId, int requestId);

    ParticipationRequest getValidParticipationRequest(int requestId);
}
