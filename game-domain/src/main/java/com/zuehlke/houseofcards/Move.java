package com.zuehlke.houseofcards;


/**
 * An action contains the following three elements:
 * - the player triggering the action
 * - an operation to be executed
 * - a timestamp
 */
public interface Move {

    public boolean isValid(State gameState);

    /**
     * Takes the current game state modifies it and returns
     * the new state.
     * @param   gameState current game state
     */
    public void execute(State gameState);
}
