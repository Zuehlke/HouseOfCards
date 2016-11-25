package com.zuehlke.houseofcards.moves;


import com.zuehlke.houseofcards.model.Round;


public class CallMove implements Move {

    @Override
    public boolean isValid(Round currentRound) {
        return true;    // TODO: revise
    }

    @Override
    public void execute(Round currentRound) {
        long currentCall = currentRound.getCurrentCall();
        currentRound.getCurrentPlayer().decreaseChipsStack(currentCall);
        currentRound.increasePot(currentCall);
    }
}
