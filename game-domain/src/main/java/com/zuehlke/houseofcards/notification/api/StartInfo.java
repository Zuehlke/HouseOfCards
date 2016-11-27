package com.zuehlke.houseofcards.notification.api;

import com.zuehlke.houseofcards.PlayerInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StartInfo implements Serializable {
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
