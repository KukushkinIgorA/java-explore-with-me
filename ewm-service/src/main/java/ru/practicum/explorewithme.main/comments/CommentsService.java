package ru.practicum.explorewithme.main.comments;

import ru.practicum.explorewithme.main.comments.dto.AdminUpdateCommentsDto;
import ru.practicum.explorewithme.main.comments.dto.CommentDto;
import ru.practicum.explorewithme.main.comments.dto.NewCommentDto;
import ru.practicum.explorewithme.main.dictionary.CommentStatus;

import java.util.List;

public interface CommentsService {

    CommentDto create(int userId, int eventId, NewCommentDto newCommentDto);

    List<CommentDto> getComments(int userId, int from, int size);

    CommentDto updateComment(int userId, int commentId, NewCommentDto newCommentDto);

    void deleteComment(int userId, int commentId);

    List<CommentDto> getEventComments(int eventId, List<CommentStatus> commentStatuses, int from, int size);

    List<CommentDto> updateCommentsStatus(CommentStatus commentStatus, AdminUpdateCommentsDto adminUpdateCommentsDto);
}
