package com.zuehlke.houseofcards;


public class Table {

    private Game game;

    public Table(Game game) {
        this.game = game;
    }

    public State registerPlayer(String playerName) {
        return game.addPlayer(new Player(playerName));
    }

    public State handleMove(Move move) {
        return game.handleMove(move);
    }
}
