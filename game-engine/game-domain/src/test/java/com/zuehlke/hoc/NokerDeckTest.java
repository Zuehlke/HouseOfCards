package com.zuehlke.hoc;

import com.zuehlke.hoc.Exceptions.EmptyDeckException;
import com.zuehlke.hoc.model.NokerDeck;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class NokerDeckTest {

    private NokerDeck nokerDeck;

    @Before
    public void setup() {
        nokerDeck = new NokerDeck();
    }

    @Test
    public void createNewDeck() {
        Map<Integer, Integer> cardCounter = new HashMap<>();
        for (int i = NokerDeck.LOWEST_CARD; i <= NokerDeck.HIGHEST_CARD; i++) {
            cardCounter.put(i, 0);
        }

        assertEquals(nokerDeck.getAllCards().size(), NokerDeck.NUM_CARDS_OF_SINGLE_DECK);

        List<Integer> deckCards = nokerDeck.getAllCards();
        deckCards.forEach(card -> cardCounter.put(card, cardCounter.get(card) + 1));

        cardCounter.forEach((card, amount)
                -> Assert.assertEquals((int) amount, NokerDeck.TIMES_OF_SINGLE_CARD_CONTAINED_IN_DECK));
    }

    @Test
    public void shuffle() {
        List<Integer> unshuffledDeck = nokerDeck.getAllCards();
        nokerDeck.shuffle();
        List<Integer> shuffledDeck = nokerDeck.getAllCards();

        Assert.assertEquals(shuffledDeck.size(), NokerDeck.NUM_CARDS_OF_SINGLE_DECK);
        assertNotEquals(unshuffledDeck, shuffledDeck);
    }

    @Test
    public void shuffleResetsTheDeckSize() {
        nokerDeck.shuffle();
        nokerDeck.drawCard();
        nokerDeck.drawCard();
        assertEquals(nokerDeck.getAllCards().size(), NokerDeck.NUM_CARDS_OF_SINGLE_DECK-2);
        nokerDeck.shuffle();
        assertEquals(nokerDeck.getAllCards().size(), NokerDeck.NUM_CARDS_OF_SINGLE_DECK);
    }

    @Test
    public void drawingCardsReducesNokerDeckSize() {
        nokerDeck.shuffle();

        int cardsToDraw = 10;
        for (int i = 0; i < cardsToDraw; i++) {
            nokerDeck.drawCard();
        }

        Assert.assertEquals(nokerDeck.getAllCards().size(), NokerDeck.NUM_CARDS_OF_SINGLE_DECK - cardsToDraw);

        // Draw all remaining cards:
        cardsToDraw = nokerDeck.getAllCards().size();
        for (int i = 0; i < cardsToDraw; i++) {
            nokerDeck.drawCard();
        }
        Assert.assertEquals(nokerDeck.getAllCards().size(), 0);
    }


    @Test(expected = EmptyDeckException.class)
    public void drawCardOfEmptyNokerDeck() {
        nokerDeck.shuffle();

        // Draw all cards from the deck
        for (int i = 0; i < NokerDeck.NUM_CARDS_OF_SINGLE_DECK; i++) {
            nokerDeck.drawCard();
        }
        // Draw when nokerDeck is empty => should throw an exception
        nokerDeck.drawCard();
    }

    @Test
    public void drawnCardComesFromTopOfTheNokerDeck() {
        nokerDeck.shuffle();

        for (int i = 0; i < 30; i++) {
            int topCard = nokerDeck.getAllCards().get(nokerDeck.getAllCards().size()-1);
            int drawnCard = nokerDeck.drawCard();
            assertEquals(topCard, drawnCard);
        }
    }
}