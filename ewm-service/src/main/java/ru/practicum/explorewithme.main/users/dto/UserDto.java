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
    int id;

    @NotBlank
    String email;

    @NotBlank
    String name;
}
