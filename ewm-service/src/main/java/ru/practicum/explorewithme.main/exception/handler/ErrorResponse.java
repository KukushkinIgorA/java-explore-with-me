package ru.practicum.explorewithme.main.exception.handler;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private final String status;
    private final String reason;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(String status, String reason, String message, LocalDateTime timestamp) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = timestamp;
    }
}