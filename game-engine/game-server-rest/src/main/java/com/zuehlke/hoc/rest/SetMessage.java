package com.zuehlke.hoc.rest;

import java.util.UUID;

/**
 * Used
 *
 * @author Lukas Hofmaier
 */
public class SetMessage {

    private UUID uuid;
    private long amount;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
