package ru.practicum.explorewithme.main.compilations.dto;

import lombok.*;
import ru.practicum.explorewithme.main.events.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CompilationDto {
    int id;

    List<EventShortDto> events;

    Boolean pinned;

    String title;
}
