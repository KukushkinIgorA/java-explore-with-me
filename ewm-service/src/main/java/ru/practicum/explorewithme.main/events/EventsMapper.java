package ru.practicum.explorewithme.main.events;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.categories.CategoriesMapper;
import ru.practicum.explorewithme.main.categories.model.Category;
import ru.practicum.explorewithme.main.comments.CommentsMapper;
import ru.practicum.explorewithme.main.comments.model.Comment;
import ru.practicum.explorewithme.main.dictionary.EventStateAction;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.events.dto.EventFullDto;
import ru.practicum.explorewithme.main.events.dto.EventShortDto;
import ru.practicum.explorewithme.main.events.dto.NewEventDto;
import ru.practicum.explorewithme.main.events.dto.UpdateEventUserRequestDto;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.events.model.Location;
import ru.practicum.explorewithme.main.exception.ForbiddenException;
import ru.practicum.explorewithme.main.users.UsersMapper;
import ru.practicum.explorewithme.main.users.model.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventsMapper {
    public static EventShortDto toEventShortDto(Event event, Map<Integer, Integer> eventViews) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoriesMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests() != null ? event.getConfirmedRequests().size() : 0)
                .eventDate(event.getEventDate())
                .initiator(UsersMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(eventViews.getOrDefault(event.getId(), 0))
                .build();
    }

    public static EventFullDto toEventFullDto(Event event, Map<Integer, Integer> eventViews, List<Comment> comments) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .createdOn(event.getCreated())
                .category(CategoriesMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests() != null ? event.getConfirmedRequests().size() : 0)
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(UsersMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublished())
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(eventViews.getOrDefault(event.getId(), 0))
                .comments(comments.stream().map(CommentsMapper::toCommentDto).collect(Collectors.toList()))
                .build();
    }

    public static Event toEvent(NewEventDto newEventDto, Category category, User user, Location location) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .initiator(user)
                .location(location)
                .paid(newEventDto.isPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .state(EventStatus.PENDING)
                .title(newEventDto.getTitle())
                .build();
    }

    public static Event toUpdateUserEvent(UpdateEventUserRequestDto updateEventUserRequestDto, Category category, Event event, Location location) {
        toUpdateEvent(updateEventUserRequestDto, category, event, location);
        if (updateEventUserRequestDto.getStateAction() != null && updateEventUserRequestDto.getStateAction().equals(EventStateAction.CANCEL_REVIEW)) {
            event.setState(EventStatus.CANCELED);
        } else if (updateEventUserRequestDto.getStateAction() != null && updateEventUserRequestDto.getStateAction().equals(EventStateAction.SEND_TO_REVIEW)) {
            event.setState(EventStatus.PENDING);
        }
        return event;
    }

    public static Event toUpdateAdminEvent(UpdateEventUserRequestDto updateEventUserRequestDto, Category category, Event event, Location location) {
        toUpdateEvent(updateEventUserRequestDto, category, event, location);
        if (updateEventUserRequestDto.getStateAction() != null && updateEventUserRequestDto.getStateAction().equals(EventStateAction.PUBLISH_EVENT)) {
            event.setState(EventStatus.PUBLISHED);
        } else if (updateEventUserRequestDto.getStateAction() != null && updateEventUserRequestDto.getStateAction().equals(EventStateAction.REJECT_EVENT)) {
            if (event.getState().equals(EventStatus.PUBLISHED)) {
                throw new ForbiddenException(String.format("нельзя отклонять события если оно уже %s", EventStatus.PUBLISHED));
            }
            event.setState(EventStatus.CANCELED);
        }
        return event;
    }

    private static void toUpdateEvent(UpdateEventUserRequestDto updateEventUserRequestDto, Category category, Event event, Location location) {
        event.setAnnotation(updateEventUserRequestDto.getAnnotation() != null && !updateEventUserRequestDto.getAnnotation().isBlank()
                ? updateEventUserRequestDto.getAnnotation() : event.getAnnotation());
        event.setCategory(category != null ? category : event.getCategory());
        event.setDescription(updateEventUserRequestDto.getDescription() != null && !updateEventUserRequestDto.getDescription().isBlank()
                ? updateEventUserRequestDto.getDescription() : event.getDescription());
        event.setEventDate(updateEventUserRequestDto.getEventDate() != null
                ? updateEventUserRequestDto.getEventDate() : event.getEventDate());
        event.setLocation(location != null
                ? location : event.getLocation());
        event.setPaid(updateEventUserRequestDto.getPaid() != null
                ? updateEventUserRequestDto.getPaid() : event.getPaid());
        event.setParticipantLimit(updateEventUserRequestDto.getParticipantLimit() != null
                ? updateEventUserRequestDto.getParticipantLimit() : event.getParticipantLimit());
        event.setRequestModeration(updateEventUserRequestDto.getRequestModeration() != null
                ? updateEventUserRequestDto.getRequestModeration() : event.isRequestModeration());
        event.setTitle(updateEventUserRequestDto.getTitle() != null && !updateEventUserRequestDto.getTitle().isBlank()
                ? updateEventUserRequestDto.getTitle() : event.getTitle());
    }
}
