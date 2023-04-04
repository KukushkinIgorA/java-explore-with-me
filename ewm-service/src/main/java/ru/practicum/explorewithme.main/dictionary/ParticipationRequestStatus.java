package ru.practicum.explorewithme.main.dictionary;

public enum ParticipationRequestStatus {
    PENDING("заявка в ожидании"),
    CONFIRMED("заявка подтверждена"),
    REJECTED("заявка отклонена"),
    CANCELED("заявка отменена");

    private final String label;

    ParticipationRequestStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}