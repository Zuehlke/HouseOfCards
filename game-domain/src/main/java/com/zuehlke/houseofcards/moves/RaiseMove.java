package com.zuehlke.houseofcards.moves;


import com.zuehlke.houseofcards.model.Round;




public class RaiseMove implements Move {

    private long amountOfChips;

    public RaiseMove(long amountOfChips) {
        this.amountOfChips = amountOfChips;
    }

    @Override
    public boolean isValid(Round currentRound) {
        boolean raiseAmountIsValid = amountOfChips <= currentRound.getRaiseLimit();
        return raiseAmountIsValid;  // TODO: AND with other validation conditions
    }

    @Override
    public void execute(Round currentRound) {
        currentRound.increasePot(amountOfChips);
        currentRound.getCurrentPlayer().decreaseChipsStack(amountOfChips);
    }
}
