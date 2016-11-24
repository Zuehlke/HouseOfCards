package com.zuehlke.houseofcards.liveview;

import java.util.List;
import java.util.Map;

public class State {
    private int pot;
    private List<String> players;
    private Map<String, Integer> playerCashes;
    private List<String> deck;

    public State(int pot, List<String> players, Map<String, Integer> playerCashes, List<String> deck) {
        this.pot = pot;
        this.players = players;
        this.playerCashes = playerCashes;
        this.deck = deck;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public Map<String, Integer> getPlayerCashes() {
        return playerCashes;
    }

    public void setPlayerCashes(Map<String, Integer> playerCashes) {
        this.playerCashes = playerCashes;
    }

    public int getPot() {
        return pot;
    }
}
