package ru.practicum.explorewithme.main.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.main.events.model.Location;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class NewEventDto {
    @NotBlank
    @Size(min = 20, message = "{event.annotation.size.to.short}")
    @Size(max = 2000, message = "{event.annotation.size.to.long}")
    private String annotation;

    @NotNull
    private Integer category;

    @NotBlank
    @Size(min = 20, message = "{event.description.size.to.short}")
    @Size(max = 7000, message = "{event.description.size.to.long}")
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    @NotBlank
    @Size(min = 3, message = "{event.title.size.to.short}")
    @Size(max = 120, message = "{event.title.size.to.long}")
    private String title;
}
