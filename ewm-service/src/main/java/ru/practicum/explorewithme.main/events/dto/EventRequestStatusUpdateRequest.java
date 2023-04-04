package ru.practicum.explorewithme.main.events.dto;

import lombok.*;
import ru.practicum.explorewithme.main.dictionary.ParticipationRequestStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private ParticipationRequestStatus status;
}
