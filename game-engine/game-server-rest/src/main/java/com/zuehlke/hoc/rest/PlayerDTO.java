package com.zuehlke.hoc.rest;

/**
 * Used to transmit player information.
 *
 * @author Lukas Hofmaier
 */
public class PlayerDTO {

    private String name;
    private long stack;

    public PlayerDTO(String name, long chipsStack) {
        this.name = name;
        this.stack = chipsStack;
    }

    public PlayerDTO() {
        this.name = "";
        this.stack = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

}
