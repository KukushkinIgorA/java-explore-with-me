package ru.practicum.explorewithme.main.compilations.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class NewCompilationDto {
    List<Integer> events;

    Boolean pinned;

    @NotBlank
    String title;
}
