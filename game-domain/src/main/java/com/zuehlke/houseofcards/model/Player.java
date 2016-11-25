package com.zuehlke.houseofcards.model;


import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private long chipsStack;
    private List<Integer> hand;

    public Player(String name) {
        hand = new ArrayList<>();
        this.name = name;
    }

    public void addCard(int card) {
        hand.add(card);
    }

    public int getFirstCard() {
        return hand.get(0);
    }

    public int getSecondCard() {
        return hand.get(1);
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

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", chipsStack=" + chipsStack +
                '}';
    }
}
