package com.zuehlke.houseofcards.moves;


import com.zuehlke.houseofcards.model.Round;

/**
 * An action contains the following three elements:
 * - the player triggering the action
 * - an operation to be executed
 * - a timestamp
 */
public interface Move {
    public boolean isValid(Round currentRound);
    public void execute(Round currentRound);
}
