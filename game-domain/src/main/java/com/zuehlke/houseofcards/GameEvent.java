package com.zuehlke.houseofcards;

import java.util.List;

public class GameEvent {
    private List<String> players;
    private String message;

    public GameEvent(List<String> players, String message) {
        this.players = players;
        this.message = message;
    }

    @Override
    public String toString() {
        return "GameEvent{" +
                "receiver players=" + players +
                ", message='" + message + '\'' +
                '}';
    }
}
