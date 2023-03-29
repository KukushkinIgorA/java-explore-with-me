package ru.practicum.explorewithme.main.users.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.main.users.UsersService;
import ru.practicum.explorewithme.main.users.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_PAGE_SIZE;
import static ru.practicum.explorewithme.main.param.PaginationParam.DEFAULT_START_INDEX;

/**
 *
 */
@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@Validated
public class AdminUsersController {
    private final UsersService usersService;

    @Autowired
    public AdminUsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping()
    public List<UserDto> getUsers(@RequestParam(name = "ids", required = false) List<Integer> ids,
                                  @PositiveOrZero @RequestParam(name = "from",
                                          required = false, defaultValue = DEFAULT_START_INDEX) int from,
                                  @Positive @RequestParam(name = "size",
                                          required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        log.info("Запрос пользователей");
        return usersService.getUsers(ids, from, size);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserDto userDto) {
        log.info("Запрос на добавление нового пользователя с email {}", userDto.getEmail());
        return usersService.create(userDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") int userId) {
        log.info("Запрос на удаление пользователя по id: {}", userId);
        usersService.deleteUser(userId);
    }

}
