package com.zuehlke.hoc.rest.server2bot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShowdownMessage implements Message {

    private Map<String, List<Integer>> players;

    public ShowdownMessage() {
        players = new HashMap<>();
    }

    public ShowdownMessage(Map<String, List<Integer>> players) {
        this.players = players;
    }

    public Map<String, List<Integer>> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, List<Integer>> players) {
        this.players = players;
    }
}
