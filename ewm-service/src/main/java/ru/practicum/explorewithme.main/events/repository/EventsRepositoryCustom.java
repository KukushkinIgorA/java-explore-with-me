package ru.practicum.explorewithme.main.events.repository;

import ru.practicum.explorewithme.main.dictionary.EventSort;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.events.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventsRepositoryCustom {
    List<Event> findEventsPublicFilter(String text,
                                       List<Integer> categories,
                                       Boolean paid,
                                       LocalDateTime rangeStart,
                                       LocalDateTime rangeEnd,
                                       EventSort eventSort,
                                       int from,
                                       int size);

    List<Event> findEventsAdminFilter(List<Integer> users,
                                      List<EventStatus> eventStatuses,
                                      List<Integer> categories,
                                      LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd,
                                      int from,
                                      int size);
}
