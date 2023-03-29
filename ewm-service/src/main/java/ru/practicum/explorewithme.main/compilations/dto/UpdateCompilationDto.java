package ru.practicum.explorewithme.main.compilations.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UpdateCompilationDto {
    List<Integer> events;

    Boolean pinned;

    String title;
}
