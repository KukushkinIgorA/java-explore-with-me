package ru.practicum.explorewithme.main.compilations.dto;

import lombok.*;
import ru.practicum.explorewithme.main.events.dto.EventShortDto;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    private int id;

    private List<EventShortDto> events;

    private boolean pinned;

    @Size(max = 120, message = "{compilation.title.size.to.long}")
    private String title;
}
