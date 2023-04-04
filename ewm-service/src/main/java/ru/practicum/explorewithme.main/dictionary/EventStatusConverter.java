package ru.practicum.explorewithme.main.dictionary;

import javax.persistence.AttributeConverter;

public class EventStatusConverter implements AttributeConverter<EventStatus, String> {
    @Override
    public String convertToDatabaseColumn(EventStatus eventStatus) {
        if (eventStatus == null)
            return null;

        switch (eventStatus) {
            case PENDING:
                return "1";

            case PUBLISHED:
                return "2";

            case CANCELED:
                return "3";

            default:
                throw new IllegalArgumentException(eventStatus + "not supported");
        }
    }

    @Override
    public EventStatus convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case "1":
                return EventStatus.PENDING;

            case "2":
                return EventStatus.PUBLISHED;

            case "3":
                return EventStatus.CANCELED;

            default:
                throw new IllegalArgumentException(dbData + "not supported");

        }
    }
}
