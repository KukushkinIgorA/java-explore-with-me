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
    private String app;
    private String uri;
    private Long hits;
}