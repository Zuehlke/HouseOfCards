package com.zuehlke;

import com.zuehlke.Exceptions.EmptyDeckException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class DeckTest {

    private Deck deck;

    @Before
    public void setup() {
        deck = new Deck();
    }

    @Test
    public void initialize() {
        Map<Integer, Integer> cardCounter = new HashMap<>();
        for (int i = Deck.LOWEST_CARD; i <= Deck.HIGHEST_CARD; i++) {
            cardCounter.put(i, 0);
        }

        Assert.assertEquals(deck.getSize(), 0);

        deck.initialize();
        Assert.assertEquals(deck.getSize(), Deck.NUM_CARDS_OF_SINGLE_DECK);

        List<Integer> cards = deck.getAllCards();
        cards.forEach(card -> cardCounter.put(card, cardCounter.get(card) + 1));

        cardCounter.forEach((card, amount) -> Assert.assertEquals((int) amount, Deck.TIMES_OF_SINGLE_CARD_CONTAINED_IN_DECK));
    }

    @Test
    public void shuffle() {
        deck.initialize();
        List<Integer> unshuffledCards = deck.getAllCards();

        deck.shuffle();
        List<Integer> shuffledCards = deck.getAllCards();
        Assert.assertEquals(shuffledCards.size(), Deck.NUM_CARDS_OF_SINGLE_DECK);
        assertNotEquals(unshuffledCards, shuffledCards);
    }

    @Test
    public void drawingCardsReducesDeckSize() {
        deck.initialize();
        deck.shuffle();

        Assert.assertEquals(deck.getSize(), Deck.NUM_CARDS_OF_SINGLE_DECK);

        int cardsToDraw = 10;
        for (int i = 0; i < cardsToDraw; i++) {
            deck.drawCard();
        }
        Assert.assertEquals(deck.getSize(), Deck.NUM_CARDS_OF_SINGLE_DECK - cardsToDraw);
        Assert.assertEquals(deck.getAllCards().size(), Deck.NUM_CARDS_OF_SINGLE_DECK - cardsToDraw);
        cardsToDraw = deck.getSize();
        for (int i = 0; i < cardsToDraw; i++) {
            deck.drawCard();
        }
        Assert.assertEquals(deck.getSize(), 0);
        Assert.assertEquals(deck.getAllCards().size(), 0);
    }

    @Test(expected = EmptyDeckException.class)
    public void drawCardOfEmptyDeck() {
        deck.initialize();
        deck.shuffle();

        // draw all cards from the deck
        for (int i = 0; i < Deck.NUM_CARDS_OF_SINGLE_DECK; i++) {
            deck.drawCard();
        }
        // draw when deck is empty
        deck.drawCard();
    }

    @Test
    public void drawnCardComesFromTopOfTheDeck() {
        deck.initialize();
        deck.shuffle();

        for (int i = 0; i < 30; i++) {
            int topCard = deck.getAllCards().get(deck.getSize()-1);
            int drawnCard = deck.drawCard();
            assertEquals(topCard, drawnCard);
        }
    }
}