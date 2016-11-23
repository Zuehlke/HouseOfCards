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
    }

    /**
     * Initialize the deck with {@value Deck#NUM_CARDS_OF_SINGLE_DECK} cards.
     */
    public void initialize() {
        for (int i = 0; i < NUM_CARDS_OF_SINGLE_DECK; i++) {
            cards.push(i % (HIGHEST_CARD-1)+2);
        }
    }

    /**
     * Shuffle the cards of the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards, random);
    }

    /**
     * Draw a single card from the deck.
     * @return top card of the deck
     * @throws EmptyDeckException
     */
    public int drawCard() throws EmptyDeckException {
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

    /**
     * Returns all the cards from the current deck.
     * @return List of cards of the current deck
     */
    public List<Integer> getAllCards() {
        return new ArrayList<>(cards);
    }

    public class EmptyDeckException extends Exception {
        public EmptyDeckException(String message) {
            super(message);
        }
    }
}
