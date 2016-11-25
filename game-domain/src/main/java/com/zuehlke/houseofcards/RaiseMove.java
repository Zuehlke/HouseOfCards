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
        // TODO: check if it's the players turn && amount of chips is valid for call
        // amount should be equal to the call of last callingPlayer
        return state.getCurrentPlayer().getName().equals(player);
    }

    @Override
    public void execute(State state) {
        state.increasePot(amountOfChips);
        state.getCurrentPlayer().decreaseChipsStack(amountOfChips);
    }
}
