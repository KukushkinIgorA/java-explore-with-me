package ru.practicum.explorewithme.main.requests.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.explorewithme.main.dictionary.ParticipationRequestStatus;
import ru.practicum.explorewithme.main.dictionary.ParticipationRequestStatusConverter;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    int id;

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;

    @Column(name = "state", nullable = false, length = 16)
    @Convert(converter = ParticipationRequestStatusConverter.class)
    private ParticipationRequestStatus state;
}
