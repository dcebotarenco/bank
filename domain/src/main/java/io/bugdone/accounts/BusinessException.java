package io.bugdone.accounts;

class BusinessException extends RuntimeException {
    private final String eventId;

    BusinessException(String eventId, String message) {
        super(message);
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}
