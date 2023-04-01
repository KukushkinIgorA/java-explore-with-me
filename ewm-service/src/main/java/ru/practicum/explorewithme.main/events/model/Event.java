package ru.practicum.explorewithme.main.events.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import ru.practicum.explorewithme.main.categories.model.Category;
import ru.practicum.explorewithme.main.compilations.model.Compilation;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.dictionary.EventStatusConverter;
import ru.practicum.explorewithme.main.requests.model.ParticipationRequest;
import ru.practicum.explorewithme.main.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "published")
    private LocalDateTime published;

    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;

    @OneToMany()
    @Where(clause = "state = '2'")
    @JoinColumn(name = "event_id")
    private List<ParticipationRequest> confirmedRequests;

    @OneToMany()
    @JoinColumn(name = "event_id")
    private List<ParticipationRequest> participationRequests;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "description", nullable = false, length = 7000)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "paid")
    private Boolean paid;

    @Column(name = "participant_limit")
    private int participantLimit;

    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @Column(name = "state", nullable = false, length = 16)
    @Convert(converter = EventStatusConverter.class)
    private EventStatus state;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "views")
    private int views;

    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "compilation_id")}
    )
    private List<Compilation> compilation;
}
