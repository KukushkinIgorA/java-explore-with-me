package ru.practicum.explorewithme.main.compilations.model;

import lombok.*;
import ru.practicum.explorewithme.main.events.model.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private int id;

    @Column(name = "pinned")
    private boolean pinned;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = {@JoinColumn(name = "compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    private List<Event> events;
}
