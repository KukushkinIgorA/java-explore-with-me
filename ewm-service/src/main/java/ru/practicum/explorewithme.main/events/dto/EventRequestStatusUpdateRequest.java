package ru.practicum.explorewithme.main.events.dto;

import lombok.*;
import ru.practicum.explorewithme.main.dictionary.ParticipationRequestStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class EventRequestStatusUpdateRequest {
    List<Integer> requestIds;
    ParticipationRequestStatus status;
}
