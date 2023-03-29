package ru.practicum.explorewithme.main.categories.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    int id;

    @Column(name = "name", nullable = false, length = 32)
    String name;
}
