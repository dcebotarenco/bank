package io.bugdone.projection.accounts;

import java.util.Optional;

class BusinessException extends RuntimeException {
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
