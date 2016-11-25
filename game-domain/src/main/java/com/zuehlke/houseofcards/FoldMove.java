package com.zuehlke.houseofcards;

public class FoldMove implements Move {

    private String player;

    public FoldMove(String player) {
        this.player = player;
    }

    @Override
    public boolean isValid(State gameState) {
        boolean isCurrentPlayersTurn = gameState.getCurrentPlayer().getName().equals(player);
        return isCurrentPlayersTurn;
    }

    @Override
    public void execute(State gameState) {
        // gameState.getMatchPlayers.remove(player)
    }
}
