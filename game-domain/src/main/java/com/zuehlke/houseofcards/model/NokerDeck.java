package com.zuehlke.houseofcards.model;

import com.zuehlke.houseofcards.Exceptions.EmptyDeckException;

import java.util.*;


/**
 * This class implements a deck of cards of the Noker game.
 * A deck consists of 52 cards with four different groups (colors).
 * However, in Noker the colors are not considered.
 * In general, there exist 13 different cards (from 2 until ace),
 * therefore each card is contained 4 times in the deck.
 */
public class Deck {

    public final static int LOWEST_CARD = 2;
    public final static int HIGHEST_CARD = 14;
    public final static int NUM_CARDS_OF_SINGLE_DECK = 52;
    public static final int TIMES_OF_SINGLE_CARD_CONTAINED_IN_DECK = 4;

    private Stack<Integer> cards;

    private Random random;

    public Deck() {
        cards = new Stack<>();
        random = new Random();
        initialize();
        shuffle();
    }

    public int drawCard() {
        int card;
        try {
            card = cards.pop();
        } catch (EmptyStackException e) {
            throw new EmptyDeckException("The deck has no more cards left.");
        }
        return card;
    }

    public int getSize() {
        return cards.size();
    }

    public List<Integer> getAllCards() {
        return new ArrayList<>(cards);
    }

    /**
     * Initialize the deck with {@value Deck#NUM_CARDS_OF_SINGLE_DECK} cards.
     */
    private void initialize() {
        for (int i = 0; i < NUM_CARDS_OF_SINGLE_DECK; i++) {
            cards.push(i % (HIGHEST_CARD-1)+2);
        }
    }

    private void shuffle() {
        Collections.shuffle(cards, random);
    }
}
