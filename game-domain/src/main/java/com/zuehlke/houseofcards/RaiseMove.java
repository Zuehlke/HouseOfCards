package com.zuehlke.houseofcards;



public class RaiseMove implements Move {

    private String player;
    private long amountOfChips;

    public RaiseMove(String player, long amountOfChips) {
        this.player = player;
        this.amountOfChips = amountOfChips;
    }

    @Override
    public boolean isValid(State state) {
        boolean isPlayersTurn = state.getCurrentPlayer().getName().equals(player);
        boolean raiseAmountIsValid = amountOfChips <= state.getRaiseLimit();
        return isPlayersTurn && raiseAmountIsValid;
    }

    @Override
    public void execute(State state) {
        state.increasePot(amountOfChips);
        state.getCurrentPlayer().decreaseChipsStack(amountOfChips);
    }
}
