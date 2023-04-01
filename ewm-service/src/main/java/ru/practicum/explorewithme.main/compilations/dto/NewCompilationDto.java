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
    private List<Integer> events;

    private boolean pinned;

    @NotBlank
    private String title;
}
