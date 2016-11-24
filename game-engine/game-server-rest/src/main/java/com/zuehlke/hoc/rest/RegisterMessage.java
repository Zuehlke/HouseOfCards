package com.zuehlke.hoc.rest;

import java.util.Objects;

public class RegisterMessage {

    private String name;
    private String hostname;
    private int port;

    public String getName() {
        return name;
    }

    public RegisterMessage setName(String name) {
        this.name = name;
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
            return Objects.equals(getName(), otherBot.getName()) &&
                    Objects.equals(getHostname(), otherBot.getHostname()) &&
                    Objects.equals(getPort(), otherBot.getPort());
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHostname(), getPort());
    }

    @Override
    public String toString() {
        return String.format("%s[%s, %s, %s]",
                this.getClass().getSimpleName(), getName(), getHostname(), getPort());
    }
}
