package com.zuehlke.hoc;



public class CallCommand implements Action {

    private Match currentMatch;
    private Player callingPlayer;
    private long amountOfChips;

    public CallCommand(Match match, Player callingPlayer, long amountOfChips) {
        currentMatch = match;
        this.callingPlayer = callingPlayer;
        this.amountOfChips = amountOfChips;
    }

    @Override
    public boolean isValid() {
        // TODO: check if it's the players turn && amount of chips is valid for call
        // amount should be equal to the call of last callingPlayer
        return currentMatch.getCurrentPlayer().getName().equals(callingPlayer.getName());
    }

    @Override
    public void execute() {
        currentMatch.incrementPot(amountOfChips);
        callingPlayer.decreaseChipsStack(amountOfChips);
        // TODO: call next turn?
    }
}
