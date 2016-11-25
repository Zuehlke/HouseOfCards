package com.zuehlke.houseofcards;

public interface Game {

    public State addPlayer(Player player);
    public State handleMove(Move move);
    public boolean isReady();

}
