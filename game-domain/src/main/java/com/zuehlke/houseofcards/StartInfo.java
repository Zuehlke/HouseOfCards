package com.zuehlke.houseofcards;

import java.util.List;

public class StartInfo {

    private List<PlayerInfo> players;

    public StartInfo(List<PlayerInfo> players) {
        this.players = players;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }
}
