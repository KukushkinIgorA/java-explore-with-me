package ru.practicum.explorewithme.main.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.main.dictionary.EventStateAction;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventUserRequestDto {

    @Size(min = 20, message = "{event.annotation.size.to.short}")
    @Size(max = 2000, message = "{event.annotation.size.to.long}")
    private String annotation;
    private Integer category;

    @Size(min = 20, message = "{event.description.size.to.short}")
    @Size(max = 7000, message = "{event.description.size.to.long}")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private LocationDto location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    private EventStateAction stateAction;

    @Size(min = 3, message = "{event.title.size.to.short}")
    @Size(max = 120, message = "{event.title.size.to.long}")
    private String title;
}
