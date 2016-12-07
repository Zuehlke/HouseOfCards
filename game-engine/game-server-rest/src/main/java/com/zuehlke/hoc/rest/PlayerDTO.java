package com.zuehlke.hoc.rest;

/**
 * Used to transmit player information.
 *
 * @author Lukas Hofmaier
 */
public class PlayerDTO {

    private String name;
    private int stack;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

}
