package ru.practicum.explorewithme.main.users.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;
}
