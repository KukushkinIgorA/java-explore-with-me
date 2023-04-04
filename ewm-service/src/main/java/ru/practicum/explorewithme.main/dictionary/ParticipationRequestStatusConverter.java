package ru.practicum.explorewithme.main.dictionary;

import javax.persistence.AttributeConverter;

public class ParticipationRequestStatusConverter implements AttributeConverter<ParticipationRequestStatus, String> {
    @Override
    public String convertToDatabaseColumn(ParticipationRequestStatus participationRequestStatus) {
        if (participationRequestStatus == null)
            return null;

        switch (participationRequestStatus) {
            case PENDING:
                return "1";

            case CONFIRMED:
                return "2";

            case REJECTED:
                return "3";

            case CANCELED:
                return "4";

            default:
                throw new IllegalArgumentException(participationRequestStatus + " not supported");
        }
    }

    @Override
    public ParticipationRequestStatus convertToEntityAttribute(String dbData) {
        if (dbData == null)
            return null;

        switch (dbData) {
            case "1":
                return ParticipationRequestStatus.PENDING;

            case "2":
                return ParticipationRequestStatus.CONFIRMED;

            case "3":
                return ParticipationRequestStatus.REJECTED;

            case "4":
                return ParticipationRequestStatus.CANCELED;

            default:
                throw new IllegalArgumentException(dbData + " not supported");

        }
    }
}
