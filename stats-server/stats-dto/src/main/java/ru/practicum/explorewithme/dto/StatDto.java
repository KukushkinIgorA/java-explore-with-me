package ru.practicum.explorewithme.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class StatDto {
    String app;
    String uri;
    Long hits;
}