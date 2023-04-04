package ru.practicum.explorewithme.main.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.main.exception.NotFoundException;
import ru.practicum.explorewithme.main.users.dto.UserDto;
import ru.practicum.explorewithme.main.users.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;

    @Override
    public List<UserDto> getUsers(List<Integer> ids, int from, int size) {
        if (ids == null) {
            return usersRepository.findAll(PageRequest.of(from / size, size))
                    .stream().map(UsersMapper::toUserDto).collect(Collectors.toList());
        } else {
            return usersRepository.findAllById(ids)
                    .stream().map(UsersMapper::toUserDto).collect(Collectors.toList());
        }
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        return UsersMapper.toUserDto(usersRepository.save(UsersMapper.toUser(userDto)));
    }

    @Override
    @Transactional
    public void deleteUser(int userId) {
        getValidUser(userId);
        usersRepository.deleteById(userId);
    }

    @Override
    public User getValidUser(int userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("на сервере отстутствует пользователь c id = %s", userId)));
    }
}
