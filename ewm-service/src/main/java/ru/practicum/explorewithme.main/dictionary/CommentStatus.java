package ru.practicum.explorewithme.main.dictionary;

import java.util.Optional;

public enum CommentStatus {
    PENDING("в ожидании"),
    PUBLISHED("опубликовано"),
    CANCELED("отменено");

    private final String label;

    CommentStatus(String label) {
        this.label = label;
    }

    public static Optional<CommentStatus> from(String eventStatusString) {
        for (CommentStatus value : CommentStatus.values()) {
            if (value.name().equalsIgnoreCase(eventStatusString)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public String getLabel() {
        return label;
    }
}