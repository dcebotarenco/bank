package io.bugdone.account;

import java.time.LocalDateTime;

public abstract class BaseEvent {

    public LocalDateTime createdDate;

    public BaseEvent() {
    }

    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public boolean equals(final Object o) {
        if (o == this)
            return true;
        if (!(o instanceof BaseEvent))
            return false;
        final BaseEvent other = (BaseEvent) o;
        if (!other.canEqual((Object) this))
            return false;
        final Object this$createdDate = this.getCreatedDate();
        final Object other$createdDate = other.getCreatedDate();
        if (this$createdDate == null ? other$createdDate != null : !this$createdDate.equals(other$createdDate))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseEvent;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $createdDate = this.getCreatedDate();
        result = result * PRIME + ($createdDate == null ? 43 : $createdDate.hashCode());
        return result;
    }

    public String toString() {
        return "BaseEvent(createdDate=" + this.getCreatedDate() + ")";
    }
}
