package ru.practicum.explorewithme.main.users;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.main.users.dto.UserDto;
import ru.practicum.explorewithme.main.users.dto.UserShortDto;
import ru.practicum.explorewithme.main.users.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UsersMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static UserShortDto toUserShortDto(User user) {
        return UserShortDto.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }


    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .name(userDto.getName())
                .build();
    }
}
