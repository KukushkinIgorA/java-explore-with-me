package ru.practicum.explorewithme.main.comments;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.comments.model.Comment;
import ru.practicum.explorewithme.main.dictionary.CommentStatus;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findCommentByAuthor_Id(int userId, Pageable pageable);

    List<Comment> findCommentByEvent_Id(int eventId, Pageable pageable);

    List<Comment> findCommentByEvent_IdAndStateIn(int eventId, List<CommentStatus> commentStatuses, Pageable pageable);

    List<Comment> findCommentByIdInAndStateNot(List<Integer> commentIds, CommentStatus commentStatus);

    List<Comment> findCommentByEvent_IdAndState(int eventId, CommentStatus commentStatus);

}
