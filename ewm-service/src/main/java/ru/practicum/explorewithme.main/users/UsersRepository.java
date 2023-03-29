package ru.practicum.explorewithme.main.users;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.main.users.model.User;

public interface UsersRepository extends JpaRepository<User, Integer> {
}
