package ru.practicum.explorewithme.main.events;

import ru.practicum.explorewithme.main.categories.CategoriesMapper;
import ru.practicum.explorewithme.main.categories.model.Category;
import ru.practicum.explorewithme.main.dictionary.EventStateAction;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.events.dto.EventFullDto;
import ru.practicum.explorewithme.main.events.dto.EventShortDto;
import ru.practicum.explorewithme.main.events.dto.NewEventDto;
import ru.practicum.explorewithme.main.events.dto.UpdateEventUserRequestDto;
import ru.practicum.explorewithme.main.events.model.Event;
import ru.practicum.explorewithme.main.exception.ForbiddenException;
import ru.practicum.explorewithme.main.users.UsersMapper;
import ru.practicum.explorewithme.main.users.model.User;

public class EventsMapper {
    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoriesMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests() != null ? event.getConfirmedRequests().size() : 0)
                .eventDate(event.getEventDate())
                .initiator(UsersMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .createdOn(event.getCreated())
                .category(CategoriesMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests() != null ? event.getConfirmedRequests().size() : 0)
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(UsersMapper.toUserShortDto(event.getInitiator()))
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublished())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(NewEventDto newEventDto, Category category, User user) {
        return Event.builder()
                .annotation(newEventDto.getAnnotation())
                .category(category)
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .initiator(user)
                .location(newEventDto.getLocation())
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .state(EventStatus.PENDING)
                .title(newEventDto.getTitle())
                .build();
    }

    public static Event toUpdateUserEvent(UpdateEventUserRequestDto updateEventUserRequestDto, Category category, Event event) {
        toUpdateEvent(updateEventUserRequestDto, category, event);
        if (updateEventUserRequestDto.getStateAction() != null && updateEventUserRequestDto.getStateAction().equals(EventStateAction.CANCEL_REVIEW)) {
            event.setState(EventStatus.CANCELED);
        } else if (updateEventUserRequestDto.getStateAction() != null && updateEventUserRequestDto.getStateAction().equals(EventStateAction.SEND_TO_REVIEW)) {
            event.setState(EventStatus.PENDING);
        }
        return event;
    }

    public static Event toUpdateAdminEvent(UpdateEventUserRequestDto updateEventUserRequestDto, Category category, Event event) {
        toUpdateEvent(updateEventUserRequestDto, category, event);
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

    private static void toUpdateEvent(UpdateEventUserRequestDto updateEventUserRequestDto, Category category, Event event) {
        event.setAnnotation(updateEventUserRequestDto.getAnnotation() != null
                ? updateEventUserRequestDto.getAnnotation() : event.getAnnotation());
        event.setCategory(category != null ? category : event.getCategory());
        event.setDescription(updateEventUserRequestDto.getDescription() != null
                ? updateEventUserRequestDto.getDescription() : event.getDescription());

        event.setEventDate(updateEventUserRequestDto.getEventDate() != null
                ? updateEventUserRequestDto.getEventDate() : event.getEventDate());
        event.setLocation(updateEventUserRequestDto.getLocation() != null
                ? updateEventUserRequestDto.getLocation() : event.getLocation());
        event.setPaid(updateEventUserRequestDto.getPaid() != null
                ? updateEventUserRequestDto.getPaid() : event.getPaid());
        event.setParticipantLimit(updateEventUserRequestDto.getParticipantLimit() != null
                ? updateEventUserRequestDto.getParticipantLimit() : event.getParticipantLimit());
        event.setRequestModeration(updateEventUserRequestDto.getRequestModeration() != null
                ? updateEventUserRequestDto.getRequestModeration() : event.getRequestModeration());
        event.setTitle(updateEventUserRequestDto.getTitle() != null
                ? updateEventUserRequestDto.getTitle() : event.getTitle());
    }
}
