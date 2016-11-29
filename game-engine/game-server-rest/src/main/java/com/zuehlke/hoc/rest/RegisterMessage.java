package com.zuehlke.hoc.rest;

import java.util.Objects;
import java.util.Optional;

public class RegisterMessage {
    private static final int MIN_PORT_NUMBER = 1;
    private static final int MAX_PORT_NUMBER = 65535;

    private String playerName;
    private String hostname;
    private int port;

    public String getPlayerName() {
        return playerName;
    }

    public RegisterMessage setPlayerName(String playerName) {
        this.playerName = playerName;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public RegisterMessage setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public int getPort() {
        return port;
    }

    public RegisterMessage setPort(int port) {
        this.port = port;
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) { return false; }
        else if (other == this) { return true; }
        else if (!(other instanceof RegisterMessage)) { return false; }
        else {
            RegisterMessage otherBot = (RegisterMessage) other;
            return Objects.equals(getPlayerName(), otherBot.getPlayerName()) &&
                    Objects.equals(getHostname(), otherBot.getHostname()) &&
                    Objects.equals(getPort(), otherBot.getPort());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayerName(), getHostname(), getPort());
    }

    @Override
    public String toString() {
        return String.format("%s[%s, %s, %s]",
                this.getClass().getSimpleName(), getPlayerName(), getHostname(), getPort());
    }

    public Optional<String> validate() {
        Optional<String> returnMessage = Optional.empty();
        if (playerName == null || playerName.isEmpty()) {
            returnMessage = Optional.of("player name is empty");
        }
        if (hostname == null || hostname.isEmpty()) {
            returnMessage = Optional.of("hostname name is empty");
        }
        if (!(port < MIN_PORT_NUMBER) && !(port < MAX_PORT_NUMBER)) {
            returnMessage = Optional.of("port is invalid");
        }
        return returnMessage;
    }
}
