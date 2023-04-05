package ru.practicum.explorewithme.main.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.comments.CommentsService;
import ru.practicum.explorewithme.main.comments.dto.CommentDto;
import ru.practicum.explorewithme.main.comments.dto.NewCommentDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_PAGE_SIZE;
import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_START_INDEX;

/**
 *
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class PrivateCommentsController {
    private final CommentsService commentsService;


    @PostMapping("{userId}/events/{eventId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable("userId") int userId,
                             @PathVariable("eventId") int eventId,
                             @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Запрос на добавление нового комментария пользователем {} к событию {}", userId, eventId);
        return commentsService.create(userId, eventId, newCommentDto);
    }

    @GetMapping("/{userId}/comments")
    public List<CommentDto> getComments(@PathVariable("userId") int userId,
                                        @PositiveOrZero @RequestParam(name = "from",
                                                defaultValue = DEFAULT_START_INDEX) int from,
                                        @Positive @RequestParam(name = "size",
                                                defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.info("Запрос всех комментариев пользователя {}", userId);
        return commentsService.getComments(userId, from, size);
    }

    @PatchMapping("/{userId}/comments/{commentId}")
    public CommentDto updateComment(@PathVariable("userId") int userId,
                                    @PathVariable("commentId") int commentId,
                                    @Valid @RequestBody NewCommentDto newCommentDto) {
        log.info("Запрос на обновление комментария {} текущего пользователем {}", commentId, userId);
        return commentsService.updateComment(userId, commentId, newCommentDto);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("userId") int userId,
                              @PathVariable("commentId") int commentId) {
        log.info("Запрос на удаление комментария {} текущего пользователем {}", commentId, userId);
        commentsService.deleteComment(userId, commentId);
    }

}
