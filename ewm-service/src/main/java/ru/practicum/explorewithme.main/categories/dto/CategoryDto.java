package ru.practicum.explorewithme.main.categories.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private int id;

    @NotBlank
    @Size(max = 32, message = "{category.name.size.to.long}")
    private String name;
}
