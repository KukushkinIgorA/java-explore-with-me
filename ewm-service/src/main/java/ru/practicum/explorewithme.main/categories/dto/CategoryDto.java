package ru.practicum.explorewithme.main.categories.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class CategoryDto {
    private int id;

    @NotBlank
    private String name;
}
