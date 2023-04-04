package ru.practicum.explorewithme.main.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.events.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventsRepository extends JpaRepository<Event, Integer>, EventsRepositoryCustom {

    List<Event> findEventByInitiator_Id(int userId, Pageable pageable);

    List<Event> findEventByCompilation_Id(int compilationId);

    Optional<Event> findEventByIdAndInitiator_Id(int eventId, int userId);

}
