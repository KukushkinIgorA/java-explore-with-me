package ru.practicum.explorewithme.main.users.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    int id;

    @Column(name = "email", nullable = false)
    String email;

    @Column(name = "name", nullable = false, length = 32)
    String name;
}
