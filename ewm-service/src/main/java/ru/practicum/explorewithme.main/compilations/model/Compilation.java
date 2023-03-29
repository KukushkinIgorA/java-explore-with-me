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
    private Boolean pinned;

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @OneToMany()
    @JoinColumn(name = "compilation_id")
    private List<Event> events;
}
