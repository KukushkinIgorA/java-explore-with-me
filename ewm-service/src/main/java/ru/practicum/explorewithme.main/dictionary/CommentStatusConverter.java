package ru.practicum.explorewithme.main.dictionary;

import javax.persistence.AttributeConverter;

public class CommentStatusConverter implements AttributeConverter<CommentStatus, String> {
    @Override
    public String convertToDatabaseColumn(CommentStatus commentStatus) {
        if (commentStatus == null)
            return null;

        switch (commentStatus) {
            case PENDING:
                return "1";

            case PUBLISHED:
                return "2";

            case CANCELED:
                return "3";

            default:
                throw new IllegalArgumentException(commentStatus + "not supported");
        }
    }

    @Override
    public CommentStatus convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case "1":
                return CommentStatus.PENDING;

            case "2":
                return CommentStatus.PUBLISHED;

            case "3":
                return CommentStatus.CANCELED;

            default:
                throw new IllegalArgumentException(dbData + "not supported");

        }
    }
}
