package ru.practicum.explorewithme.main.comments;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import ru.practicum.explorewithme.main.comments.dto.AdminUpdateCommentsDto;
import ru.practicum.explorewithme.main.comments.dto.CommentDto;
import ru.practicum.explorewithme.main.comments.dto.NewCommentDto;
import ru.practicum.explorewithme.main.comments.model.Comment;
import ru.practicum.explorewithme.main.dictionary.CommentStatus;
import ru.practicum.explorewithme.main.events.EventsService;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.exception.ForbiddenException;
import ru.practicum.explorewithme.main.exception.NotFoundException;
import ru.practicum.explorewithme.main.users.UsersService;
import ru.practicum.explorewithme.main.users.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentsServiceImpl implements CommentsService {

    private final CommentsRepository commentsRepository;

    private final UsersService usersService;

    private final EventsService eventsService;

    @Override
    @Transactional
    public CommentDto create(int userId, int eventId, NewCommentDto newCommentDto) {
        User user = usersService.getValidUser(userId);
        Event event = eventsService.getValidEvent(eventId);
        if (event.getConfirmedRequests()
                .stream().noneMatch(participationRequest -> participationRequest.getRequester().getId() == userId)) {
            throw new ForbiddenException(String.format("нельзя оставлять комментарий на событие %s в котором не являешься участником", eventId));
        }
        return CommentsMapper.toCommentDto(commentsRepository.save(CommentsMapper.toComment(newCommentDto, event, user)));
    }

    @Override
    public List<CommentDto> getComments(int userId, int from, int size) {
        usersService.getValidUser(userId);
        return commentsRepository.findCommentByAuthor_Id(userId, PageRequest.of(from / size, size))
                .stream().map(CommentsMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommentDto updateComment(int userId, int commentId, NewCommentDto newCommentDto) {
        usersService.getValidUser(userId);
        Comment comment = getValidComment(commentId);
        if (comment.getAuthor().getId() != userId || !comment.getState().equals(CommentStatus.PENDING)) {
            throw new ForbiddenException(String.format("нельзя обновить комментарий пользователя %s в статусе %s", userId, comment.getState()));
        }
        comment.setText(newCommentDto.getText());
        return CommentsMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteComment(int userId, int commentId) {
        usersService.getValidUser(userId);
        Comment comment = getValidComment(commentId);
        if (comment.getAuthor().getId() != userId) {
            throw new ForbiddenException(String.format("нельзя обновить комментарий пользователя %s", comment.getAuthor().getId()));
        }
        commentsRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> getEventComments(int eventId, List<CommentStatus> commentStatuses, int from, int size) {
        eventsService.getValidEvent(eventId);
        List<Comment> comments;
        if (CollectionUtils.isEmpty(commentStatuses)) {
            comments = commentsRepository.findCommentByEvent_Id(eventId, PageRequest.of(from / size, size));
        } else {
            comments = commentsRepository.findCommentByEvent_IdAndStateIn(eventId, commentStatuses, PageRequest.of(from / size, size));
        }
        return comments.stream().map(CommentsMapper::toCommentDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CommentDto> updateCommentsStatus(CommentStatus commentStatus, AdminUpdateCommentsDto adminUpdateCommentsDto) {
        List<Comment> comments = commentsRepository.findCommentByIdInAndStateNot(adminUpdateCommentsDto.getCommentIds(), commentStatus);
        for (Comment comment : comments) {
            comment.setState(commentStatus);
        }
        return comments.stream().map(CommentsMapper::toCommentDto).collect(Collectors.toList());
    }


    public Comment getValidComment(int commentId) {
        return commentsRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("на сервере отстутствует комментарий c id = %s", commentId)));
    }
}
