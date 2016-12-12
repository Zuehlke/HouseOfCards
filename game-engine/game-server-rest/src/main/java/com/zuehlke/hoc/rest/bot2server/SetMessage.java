package com.zuehlke.hoc.rest.bot2server;


import java.util.UUID;

/**
 * Used
 *
 * @author Lukas Hofmaier
 */
public class SetMessage implements Bot2ServerMessage {

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
