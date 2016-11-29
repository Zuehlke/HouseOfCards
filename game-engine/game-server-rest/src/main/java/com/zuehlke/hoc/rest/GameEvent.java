package com.zuehlke.hoc.rest;

/**
 * Notifies the player that the game state has changed.
 *
 * @author Lukas Hofmaier
 */
public class GameEvent {

    public EventKind eventKind;

    public EventKind getEventKind() {
        return eventKind;
    }

    public void setEventKind(EventKind eventKind) {
        this.eventKind = eventKind;
    }

    @Override
    public String toString() {
        return String.format("GameEvent: %s", eventKind.toString());
    }

    public enum EventKind {
        START, RESERVATION_CONFIRMATION, NAME_ALREADY_TAKEN, URI_ALREADY_TAKEN, REGISTRATION_CLOSED
    }

}
