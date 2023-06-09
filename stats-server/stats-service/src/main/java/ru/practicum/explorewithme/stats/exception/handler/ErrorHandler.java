package ru.practicum.explorewithme.stats.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.stats.exception.NotFoundException;
import ru.practicum.explorewithme.stats.exception.ValidationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.error("Ошибка not found", e);
        return new ErrorResponse(
                String.format("Ошибка %s: %s", e.getClass().getSimpleName(), e.getMessage())
        );
    }

    @ExceptionHandler({NumberFormatException.class, ValidationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(Throwable e) {
        log.error("Ошибка bad request", e);
        return new ErrorResponse(
                String.format("Ошибка %s: %s", e.getClass().getSimpleName(), e.getMessage())
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error("Непредвиденная ошибка", e);
        return new ErrorResponse(
                String.format("Произошла непредвиденная ошибка: %s", e.getMessage())
        );
    }
}
