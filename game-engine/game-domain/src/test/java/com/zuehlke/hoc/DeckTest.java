package com.zuehlke.hoc;

import com.zuehlke.hoc.model.NokerDeck;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class DeckTest {

//    private NokerDeck NokerDeck;
//
//    @Before
//    public void setup() {
//        NokerDeck = new NokerDeck();
//    }
//
//    @Test
//    public void initialize() {
//        Map<Integer, Integer> cardCounter = new HashMap<>();
//        for (int i = NokerDeck.LOWEST_CARD; i <= NokerDeck.HIGHEST_CARD; i++) {
//            cardCounter.put(i, 0);
//        }
//
//        Assert.assertEquals(NokerDeck.getSize(), 0);
//
//        NokerDeck.initialize();
//        Assert.assertEquals(NokerDeck.getSize(), NokerDeck.NUM_CARDS_OF_SINGLE_NokerDeck);
//
//        List<Integer> cards = NokerDeck.getAllCards();
//        cards.forEach(card -> cardCounter.put(card, cardCounter.get(card) + 1));
//
//        cardCounter.forEach((card, amount) -> Assert.assertEquals((int) amount, NokerDeck.TIMES_OF_SINGLE_CARD_CONTAINED_IN_NokerDeck));
//    }
//
//    @Test
//    public void shuffle() {
//        NokerDeck.initialize();
//        List<Integer> unshuffledCards = NokerDeck.getAllCards();
//
//        NokerDeck.shuffle();
//        List<Integer> shuffledCards = NokerDeck.getAllCards();
//        Assert.assertEquals(shuffledCards.size(), NokerDeck.NUM_CARDS_OF_SINGLE_NokerDeck);
//        assertNotEquals(unshuffledCards, shuffledCards);
//    }
//
//    @Test
//    public void drawingCardsReducesNokerDeckSize() {
//        NokerDeck.initialize();
//        NokerDeck.shuffle();
//
//        Assert.assertEquals(NokerDeck.getSize(), NokerDeck.NUM_CARDS_OF_SINGLE_NokerDeck);
//
//        int cardsToDraw = 10;
//        for (int i = 0; i < cardsToDraw; i++) {
//            NokerDeck.drawCard();
//        }
//        Assert.assertEquals(NokerDeck.getSize(), NokerDeck.NUM_CARDS_OF_SINGLE_NokerDeck - cardsToDraw);
//        Assert.assertEquals(NokerDeck.getAllCards().size(), NokerDeck.NUM_CARDS_OF_SINGLE_NokerDeck - cardsToDraw);
//        cardsToDraw = NokerDeck.getSize();
//        for (int i = 0; i < cardsToDraw; i++) {
//            NokerDeck.drawCard();
//        }
//        Assert.assertEquals(NokerDeck.getSize(), 0);
//        Assert.assertEquals(NokerDeck.getAllCards().size(), 0);
//    }
//
//    @Test(expected = EmptyNokerDeckException.class)
//    public void drawCardOfEmptyNokerDeck() {
//        NokerDeck.initialize();
//        NokerDeck.shuffle();
//
//        // draw all cards from the NokerDeck
//        for (int i = 0; i < NokerDeck.NUM_CARDS_OF_SINGLE_NokerDeck; i++) {
//            NokerDeck.drawCard();
//        }
//        // draw when NokerDeck is empty
//        NokerDeck.drawCard();
//    }
//
//    @Test
//    public void drawnCardComesFromTopOfTheNokerDeck() {
//        NokerDeck.initialize();
//        NokerDeck.shuffle();
//
//        for (int i = 0; i < 30; i++) {
//            int topCard = NokerDeck.getAllCards().get(NokerDeck.getSize()-1);
//            int drawnCard = NokerDeck.drawCard();
//            assertEquals(topCard, drawnCard);
//        }
//    }
}