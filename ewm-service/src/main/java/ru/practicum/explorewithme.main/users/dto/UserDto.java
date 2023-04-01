package ru.practicum.explorewithme.main.users.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserDto {
    private int id;

    @NotBlank
    private String email;

    @NotBlank
    private String name;
}
