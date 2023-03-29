package ru.practicum.explorewithme.main.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.dictionary.ParticipationRequestStatus;
import ru.practicum.explorewithme.main.requests.model.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestsRepository extends JpaRepository<ParticipationRequest, Integer> {
    ParticipationRequest findParticipationRequestByEvent_IdAndRequester_Id(int eventId, int userId);

    List<ParticipationRequest> findParticipationRequestByRequester_Id(int userId);

    List<ParticipationRequest> findParticipationRequestByEvent_IdAndState(int eventId, ParticipationRequestStatus participationRequestStatus);

}
