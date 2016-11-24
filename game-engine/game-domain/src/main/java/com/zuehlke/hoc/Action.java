package com.zuehlke.hoc;


/**
 * An action contains the following three elements:
 * - the player triggering the action
 * - an operation to be executed
 * - a timestamp
 */
public abstract interface Action {

    public boolean isValid();
    public void execute();
}
