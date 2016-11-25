package com.zuehlke.houseofcards;

import java.util.ArrayList;
import java.util.List;

public class StartInfo {
    private List<PlayerInfo> playerInfos;

    public StartInfo() {
        playerInfos = new ArrayList<>();
    }

    public List<PlayerInfo> getPlayerInfos() {
        return playerInfos;
    }

    public void addPlayerInfo(PlayerInfo playerInfo) {
        playerInfos.add(playerInfo);
    }

    @Override
    public String toString() {
        return "StartInfo{" +
                "playerInfos=" + playerInfos +
                '}';
    }
}
