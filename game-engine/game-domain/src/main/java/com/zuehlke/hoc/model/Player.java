package com.zuehlke.hoc.model;


import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private long chipsStack = 0;
    private List<Integer> hand;

    public Player(String name) {
        hand = new ArrayList<>();
        this.name = name;
    }

    public void cleanHand() {
        hand.clear();
    }

    public void addCard(int card) {
        hand.add(card);
    }

    public int getFirstCard() {
        //TODO: this shouldn't be a C style return..
        if(hand.size() < 1){
            return -1;
        }
        return hand.get(0);
    }

    public int getSecondCard() {
        if(hand.size() < 2){
            return -1;
        }
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
