package ru.practicum.explorewithme.main.comments.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.practicum.explorewithme.main.dictionary.CommentStatus;
import ru.practicum.explorewithme.main.dictionary.CommentStatusConverter;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int id;

    @CreationTimestamp
    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "state", nullable = false, length = 16)
    @Convert(converter = CommentStatusConverter.class)
    private CommentStatus state;

    @Column(name = "text", nullable = false, length = 7000)
    private String text;
}
