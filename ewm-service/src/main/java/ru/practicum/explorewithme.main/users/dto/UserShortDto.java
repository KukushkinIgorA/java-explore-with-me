package ru.practicum.explorewithme.main.users.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserShortDto {
    private int id;

    @NotBlank
    private String name;
}
