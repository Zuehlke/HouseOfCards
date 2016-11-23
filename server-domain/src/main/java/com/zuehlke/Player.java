package com.zuehlke;



public class Player {

    private String name;
    private long chips;
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

    public long getChips() {
        return chips;
    }

    public void setChips(long chips) {
        this.chips = chips;
    }
}
