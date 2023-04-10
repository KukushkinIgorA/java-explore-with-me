package ru.practicum.explorewithme.main.comments.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUpdateCommentsDto {
    @NotEmpty
    List<Integer> commentIds;
}