package ru.practicum.explorewithme.main.compilations.dto;

import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompilationDto {
    private List<Integer> events;

    private boolean pinned;

    @Size(max = 120, message = "{compilation.title.size.to.long}")
    private String title;
}
