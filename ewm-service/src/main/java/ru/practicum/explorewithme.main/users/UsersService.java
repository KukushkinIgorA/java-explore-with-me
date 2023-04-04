package ru.practicum.explorewithme.main.users;

import ru.practicum.explorewithme.main.users.dto.UserDto;
import ru.practicum.explorewithme.main.users.model.User;

import java.util.List;

public interface UsersService {

    List<UserDto> getUsers(List<Integer> ids, int from, int size);

    UserDto create(UserDto userDto);

    void deleteUser(int userId);

    User getValidUser(int userId);
}
