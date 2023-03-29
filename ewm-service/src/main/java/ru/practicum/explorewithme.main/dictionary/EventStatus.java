package ru.practicum.explorewithme.main.dictionary;

import java.util.Optional;

public enum EventStatus {
    PENDING("в ожидании"),
    PUBLISHED("опубликовано"),
    CANCELED("отменено");

    private final String label;

    EventStatus(String label) {
        this.label = label;
    }

    public static Optional<EventStatus> from(String eventStatusString) {
        for (EventStatus value : EventStatus.values()) {
            if (value.name().equals(eventStatusString)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public String getLabel() {
        return label;
    }
}