package ru.practicum.explorewithme.main.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.main.categories.dto.CategoryDto;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.events.model.Location;
import ru.practicum.explorewithme.main.users.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class EventFullDto {
    private int id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;
    private Boolean requestModeration;
    private EventStatus state;
    private String title;
    private int views;
}
