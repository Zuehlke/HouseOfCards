package com.zuehlke.hoc.model;


import com.zuehlke.hoc.Exceptions.ExceedHandSizeException;

import java.util.Optional;

public class Player {

    private String name;
    private long chipsStack = 0;
    private Optional<Integer> firstCard;
    private Optional<Integer> secondCard;

    public Player(String name) {
        cleanHand();
        this.name = name;
    }

    public void cleanHand() {
        firstCard = Optional.empty();
        secondCard = Optional.empty();
    }

    public void addCard(int card) {
        if (!firstCard.isPresent()) {
            firstCard = Optional.of(card);
        } else if (!secondCard.isPresent()) {
            secondCard = Optional.of(card);
        } else {
            throw new ExceedHandSizeException("too many cards on hand added");
        }
    }

    public Optional<Integer> getFirstCard() {
        return firstCard;
    }

    public Optional<Integer> getSecondCard() {
        return secondCard;
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
