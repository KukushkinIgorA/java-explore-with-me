package ru.practicum.explorewithme.main.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.main.dictionary.EventStateAction;
import ru.practicum.explorewithme.main.events.model.Location;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UpdateEventUserRequestDto {

    @Size(min = 20, message = "{event.annotation.size.to.short}")
    @Size(max = 2000, message = "{event.annotation.size.to.long}")
    String annotation;
    Integer category;

    @Size(min = 20, message = "{event.description.size.to.short}")
    @Size(max = 7000, message = "{event.description.size.to.long}")
    String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    EventStateAction stateAction;

    @Size(min = 3, message = "{event.title.size.to.short}")
    @Size(max = 120, message = "{event.title.size.to.long}")
    String title;
}
