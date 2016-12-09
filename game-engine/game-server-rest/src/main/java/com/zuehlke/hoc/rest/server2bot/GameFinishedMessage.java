package com.zuehlke.hoc.rest.server2bot;


public class GameFinishedMessage implements Message {

    private String winner;

    public GameFinishedMessage() {
        winner = "";
    }

    public GameFinishedMessage(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
