package ru.practicum.explorewithme.main.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.events.model.Location;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}
