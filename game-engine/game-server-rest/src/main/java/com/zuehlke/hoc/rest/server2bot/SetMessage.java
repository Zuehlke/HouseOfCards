package com.zuehlke.hoc.rest.server2bot;


public class SetMessage implements Message {

    private String playerName;
    private long amount;

    public SetMessage() {
        playerName = "";
        amount = 0;
    }

    public SetMessage(String playerName, long amount) {
        this.playerName = playerName;
        this.amount = amount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
