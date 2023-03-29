package ru.practicum.explorewithme.main.events.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    @JsonIgnore
    int id;

    @Column(name = "lat", nullable = false)
    Float lat;

    @Column(name = "lon", nullable = false)
    Float lon;
}
