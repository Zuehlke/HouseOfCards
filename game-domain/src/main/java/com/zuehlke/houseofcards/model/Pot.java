package com.zuehlke.houseofcards.model;

public class Pot {

    private long amountOfChips;

    public Pot() {
        amountOfChips = 0;
    }

    public void increase(long amountOfChips) {
        this.amountOfChips += amountOfChips;
    }

    public void decrease(long amountOfChips) {
        this.amountOfChips -= amountOfChips;
    }
}
