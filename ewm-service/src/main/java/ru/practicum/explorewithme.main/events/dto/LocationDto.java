package ru.practicum.explorewithme.main.events.dto;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDto {
    @NotNull
    private Float lat;

    @NotNull
    private Float lon;
}
