package ru.practicum.explorewithme.main.comments;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.comments.dto.CommentDto;
import ru.practicum.explorewithme.main.comments.dto.NewCommentDto;
import ru.practicum.explorewithme.main.comments.model.Comment;
import ru.practicum.explorewithme.main.dictionary.CommentStatus;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.users.UsersMapper;
import ru.practicum.explorewithme.main.users.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentsMapper {
    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .created(comment.getCreated())
                .updated(comment.getUpdated())
                .author(UsersMapper.toUserDto(comment.getAuthor()))
                .state(comment.getState())
                .text(comment.getText())
                .build();
    }

    public static Comment toComment(NewCommentDto newCommentDto, Event event, User user) {
        return Comment.builder()
                .event(event)
                .author(user)
                .state(CommentStatus.PENDING)
                .text(newCommentDto.getText())
                .build();
    }
}
