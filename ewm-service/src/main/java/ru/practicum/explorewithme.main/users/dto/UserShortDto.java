package ru.practicum.explorewithme.main.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class UserShortDto {
    int id;
    String name;
}
