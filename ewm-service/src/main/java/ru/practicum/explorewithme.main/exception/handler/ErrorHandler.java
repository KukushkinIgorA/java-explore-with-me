package ru.practicum.explorewithme.main.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.explorewithme.main.exception.ConflictException;
import ru.practicum.explorewithme.main.exception.ForbiddenException;
import ru.practicum.explorewithme.main.exception.NotFoundException;
import ru.practicum.explorewithme.main.exception.ValidationException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.error("Ошибка not found", e);
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(),
                e.getClass().getSimpleName(),
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler({NumberFormatException.class,
            ValidationException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRuntimeException(Throwable e) {
        log.error("Ошибка bad request", e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                e.getClass().getSimpleName(),
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflictException(Throwable e) {
        log.error("Ошибка bad request", e);
        return new ErrorResponse(HttpStatus.CONFLICT.toString(),
                e.getClass().getSimpleName(),
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenException(Throwable e) {
        log.error("Ошибка bad request", e);
        return new ErrorResponse(HttpStatus.FORBIDDEN.toString(),
                e.getClass().getSimpleName(),
                e.getMessage(),
                LocalDateTime.now()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        log.error("Непредвиденная ошибка", e);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                e.getClass().getSimpleName(),
                e.getMessage(),
                LocalDateTime.now()
        );
    }
}
