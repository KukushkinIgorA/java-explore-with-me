package ru.practicum.explorewithme.stats.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hit_id")
    int id;

    @Column(name = "app", nullable = false, length = 32)
    String app;

    @Column(name = "uri", nullable = false)
    String uri;

    @Column(name = "ip", nullable = false, length = 16)
    String ip;

    @Column(name = "hit_timestamp", nullable = false)
    private LocalDateTime timestamp;
}
