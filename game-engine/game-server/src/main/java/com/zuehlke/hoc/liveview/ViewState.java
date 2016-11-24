package com.zuehlke.hoc.liveview;

import java.util.List;
import java.util.Map;

public class ViewState {
    private int pot;
    private List<String> players;
    private Map<String, Integer> playerCashes;

    public ViewState(State state) {
        this.pot = state.getPot();
        this.playerCashes = state.getPlayerCashes();
        this.players = state.getPlayers();
    }

    public int getPot() {
        return pot;
    }

    public List<String> getPlayers() {
        return players;
    }

    public Map<String, Integer> getPlayerCashes() {
        return playerCashes;
    }
}
