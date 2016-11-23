package com.zuehlke.websocket;

import java.util.List;

public class State {
    int pot;
    List<String> players;

    public State(int pot, List<String> players) {
        this.pot = pot;
        this.players = players;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public int getPot() {
        return pot;
    }

    public List<String> getPlayers() {
        return players;
    }
}
