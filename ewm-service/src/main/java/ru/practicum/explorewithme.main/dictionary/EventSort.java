package ru.practicum.explorewithme.main.dictionary;

import java.util.Optional;

public enum EventSort {
    EVENT_DATE("сортировка по дате события"),
    VIEWS("сортировка по просмотрам");

    private final String label;

    EventSort(String label) {
        this.label = label;
    }

    public static Optional<EventSort> from(String eventSortString) {
        for (EventSort value : EventSort.values()) {
            if (value.name().equalsIgnoreCase(eventSortString)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    public String getLabel() {
        return label;
    }
}