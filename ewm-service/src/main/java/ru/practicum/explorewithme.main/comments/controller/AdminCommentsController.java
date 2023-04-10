package ru.practicum.explorewithme.main.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.comments.CommentsService;
import ru.practicum.explorewithme.main.comments.dto.AdminUpdateCommentsDto;
import ru.practicum.explorewithme.main.comments.dto.CommentDto;
import ru.practicum.explorewithme.main.dictionary.CommentStatus;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_PAGE_SIZE;
import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_START_INDEX;

/**
 *
 */
@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminCommentsController {
    private final CommentsService commentsService;

    @GetMapping("events/{eventId}/comments")
    public List<CommentDto> getEventComments(@PathVariable("eventId") int eventId,
                                             @RequestParam(name = "states", required = false) List<String> states,
                                             @PositiveOrZero @RequestParam(name = "from",
                                                         defaultValue = DEFAULT_START_INDEX) int from,
                                             @Positive @RequestParam(name = "size",
                                                         defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.info("Запрос админом комментариев по событию {}", eventId);
        List<CommentStatus> commentStatuses = states.stream().map(AdminCommentsController::checkCommentStatus)
                .collect(Collectors.toList());
        return commentsService.getEventComments(eventId, commentStatuses, from, size);
    }

    @PatchMapping("/comments")
    public List<CommentDto> updateCommentsStatus(@RequestParam(name = "state", required = false) String state,
                                                 @Valid @RequestBody AdminUpdateCommentsDto adminUpdateCommentsDto) {
        log.info("Запрос админом обновления статуса комментариев на {}", state);
        CommentStatus commentStatus = checkCommentStatus(state);
        return commentsService.updateCommentsStatus(commentStatus, adminUpdateCommentsDto);
    }

    private static CommentStatus checkCommentStatus(String state) {
        return CommentStatus.from(state).orElseThrow(() ->
                new IllegalArgumentException("Unknown state: " + state));
    }

}
