package com.zuehlke.hoc;



public class Player {

    private String name;
    private long chipsStack;
    private int firstCard;
    private int secondCard;

    public Player(String name) {
        this.name = name;
    }

    public int getFirstCard() {
        return firstCard;
    }

    public void setFirstCard(int firstCard) {
        this.firstCard = firstCard;
    }

    public int getSecondCard() {
        return secondCard;
    }

    public void setSecondCard(int secondCard) {
        this.secondCard = secondCard;
    }

    public String getName() {
        return name;
    }

    public long getChipsStack() {
        return chipsStack;
    }

    public void setChipsStack(long chipsStack) {
        this.chipsStack = chipsStack;
    }

    public void increaseChipsStack(long amountOfChips) {
        chipsStack += amountOfChips;
    }

    public void decreaseChipsStack(long amountOfChips) {
        chipsStack -= amountOfChips;
    }
}
