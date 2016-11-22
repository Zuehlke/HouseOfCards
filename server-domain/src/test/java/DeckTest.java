import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class DeckTest {

    // The times that a single card appears in one deck
    // => there exist 4 cards of the same value per deck
    private static final int TIMES_OF_CARD_IN_DECK = 4;

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

        assertEquals(deck.getSize(), 0);

        deck.initialize();
        assertEquals(deck.getSize(), Deck.NUM_CARDS_OF_SINGLE_DECK);

        List<Integer> cards = deck.getAllCards();
        cards.forEach(card -> cardCounter.put(card, cardCounter.get(card) + 1));

        cardCounter.forEach((card, amount) -> assertEquals((int) amount, TIMES_OF_CARD_IN_DECK));
    }

    @Test
    public void shuffle() {
        deck.initialize();
        List<Integer> unshuffledCards = deck.getAllCards();

        deck.shuffle();
        List<Integer> shuffledCards = deck.getAllCards();
        assertEquals(shuffledCards.size(), Deck.NUM_CARDS_OF_SINGLE_DECK);
        assertNotEquals(unshuffledCards, shuffledCards);
    }

    @Test
    public void drawingCardsReducesDeckSize() throws Deck.EmptyDeckException {
        deck.initialize();
        deck.shuffle();

        assertEquals(deck.getSize(), Deck.NUM_CARDS_OF_SINGLE_DECK);

        int cardsToDraw = 10;
        for (int i = 0; i < cardsToDraw; i++) {
            deck.drawCard();
        }
        assertEquals(deck.getSize(), Deck.NUM_CARDS_OF_SINGLE_DECK - cardsToDraw);
        assertEquals(deck.getAllCards().size(), Deck.NUM_CARDS_OF_SINGLE_DECK - cardsToDraw);
        cardsToDraw = deck.getSize();
        for (int i = 0; i < cardsToDraw; i++) {
            deck.drawCard();
        }
        assertEquals(deck.getSize(), 0);
        assertEquals(deck.getAllCards().size(), 0);
    }

    @Test(expected = Deck.EmptyDeckException.class)
    public void drawCardOfEmptyDeck() throws Deck.EmptyDeckException {
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
    public void drawnCardComesFromTopOfTheDeck() throws Deck.EmptyDeckException {
        deck.initialize();
        deck.shuffle();

        for (int i = 0; i < 30; i++) {
            int topCard = deck.getAllCards().get(deck.getSize()-1);
            int drawnCard = deck.drawCard();
            assertEquals(topCard, drawnCard);
        }
    }
}