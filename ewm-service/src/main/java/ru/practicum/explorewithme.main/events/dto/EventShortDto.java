package ru.practicum.explorewithme.main.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.explorewithme.main.categories.dto.CategoryDto;
import ru.practicum.explorewithme.main.users.dto.UserShortDto;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventShortDto {
    private int id;

    @Size(min = 20, message = "{event.annotation.size.to.short}")
    @Size(max = 2000, message = "{event.annotation.size.to.long}")
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;

    @Size(min = 3, message = "{event.title.size.to.short}")
    @Size(max = 120, message = "{event.title.size.to.long}")
    private String title;
    private int views;
}
