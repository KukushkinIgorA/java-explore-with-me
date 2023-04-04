package ru.practicum.explorewithme.main.events.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.practicum.explorewithme.main.dictionary.EventSort;
import ru.practicum.explorewithme.main.dictionary.EventStatus;
import ru.practicum.explorewithme.main.events.model.Event;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventsRepositoryCustomImpl implements EventsRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Event> findEventsPublicFilter(String text,
                                              List<Integer> categories,
                                              Boolean paid,
                                              LocalDateTime rangeStart,
                                              LocalDateTime rangeEnd,
                                              EventSort eventSort,
                                              int from,
                                              int size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);

        Root<Event> event = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(event.get("state"), EventStatus.PUBLISHED));
        if (text != null) {
            Predicate predicateTitle = cb.like(cb.lower(event.get("annotation")),
                    "%" + text.toLowerCase() + "%");
            Predicate predicateDescription = cb.like(cb.lower(event.get("description")),
                    "%" + text.toLowerCase() + "%");
            predicates.add(cb.or(predicateTitle, predicateDescription));
        }
        if (!CollectionUtils.isEmpty(categories)) {
            predicates.add(event.get("category").in(categories));
        }
        if (paid != null) {
            predicates.add(cb.equal(event.get("paid"), paid));
        }
        if (rangeStart != null) {
            predicates.add(cb.greaterThanOrEqualTo(event.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(cb.lessThanOrEqualTo(event.get("eventDate"), rangeEnd));
        }
        if (rangeStart == null && rangeEnd == null) {
            predicates.add(cb.greaterThanOrEqualTo(event.get("eventDate"), LocalDateTime.now()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).setFirstResult(from).setMaxResults(size).getResultList();
    }

    @Override
    public List<Event> findEventsAdminFilter(List<Integer> users,
                                             List<EventStatus> eventStatuses,
                                             List<Integer> categories,
                                             LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd,
                                             int from,
                                             int size) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);

        Root<Event> event = cq.from(Event.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!CollectionUtils.isEmpty(users)) {
            predicates.add(event.get("initiator").in(users));
        }
        if (!CollectionUtils.isEmpty(eventStatuses)) {
            predicates.add(event.get("state").in(eventStatuses));
        }
        if (!CollectionUtils.isEmpty(categories)) {
            predicates.add(event.get("category").in(categories));
        }
        if (rangeStart != null) {
            predicates.add(cb.greaterThanOrEqualTo(event.get("eventDate"), rangeStart));
        }
        if (rangeEnd != null) {
            predicates.add(cb.lessThanOrEqualTo(event.get("eventDate"), rangeEnd));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).setFirstResult(from).setMaxResults(size).getResultList();
    }
}
