package com.zuehlke.hoc.rest.server2bot;



public class FoldMessage implements Message {

    private String playerName;

    public FoldMessage() {
        playerName = "";
    }

    public FoldMessage(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
