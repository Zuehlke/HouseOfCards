package com.zuehlke.hoc.rest.bot2server;


import java.util.UUID;


public class FoldMessage implements Bot2ServerMessage {

    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
