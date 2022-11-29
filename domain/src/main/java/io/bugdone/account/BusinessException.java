package io.bugdone.account;

import java.util.Optional;

public class BusinessException extends RuntimeException {
    private String eventId;

    BusinessException(String message) {
        super(message);
    }

    BusinessException(String eventId, String message) {
        super(message);
        this.eventId = eventId;
    }

    public Optional<String> getEventId() {
        return Optional.ofNullable(eventId);
    }
}
