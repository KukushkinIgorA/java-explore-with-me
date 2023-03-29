package ru.practicum.explorewithme.main.dictionary;

public enum EventStateAction {
    SEND_TO_REVIEW("оправить на ревью"),
    CANCEL_REVIEW("отменить"),

    PUBLISH_EVENT("опубликовать"),

    REJECT_EVENT("отклонить");

    private final String label;

    EventStateAction(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}