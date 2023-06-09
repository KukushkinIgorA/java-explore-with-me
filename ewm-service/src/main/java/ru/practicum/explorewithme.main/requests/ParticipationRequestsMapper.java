package ru.practicum.explorewithme.main.requests;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.dictionary.ParticipationRequestStatus;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.requests.dto.ParticipationRequestDto;
import ru.practicum.explorewithme.main.requests.model.ParticipationRequest;
import ru.practicum.explorewithme.main.users.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipationRequestsMapper {
    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return ParticipationRequestDto.builder()
                .id(participationRequest.getId())
                .created(participationRequest.getCreated())
                .event(participationRequest.getEvent().getId())
                .requester(participationRequest.getRequester().getId())
                .status(participationRequest.getState())
                .build();
    }

    public static ParticipationRequest toParticipationRequest(Event event, User user) {
        return ParticipationRequest.builder()
                .event(event)
                .requester(user)
                .state(event.isRequestModeration() ? ParticipationRequestStatus.PENDING : ParticipationRequestStatus.CONFIRMED)
                .build();
    }
}
