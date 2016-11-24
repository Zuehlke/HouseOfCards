package com.zuehlke.houseofcards;

public interface Game {

    public void addPlayer(Player p);
    public boolean isReady();
    public void start();
    public void handleAction(Action action);
}
