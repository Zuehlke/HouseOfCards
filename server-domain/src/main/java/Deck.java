import java.util.*;


public class Deck {

    public final static int LOWEST_CARD = 2;
    public final static int HIGHEST_CARD = 14;
    public final static int NUM_CARDS_OF_SINGLE_DECK = 52;

    private Stack<Integer> cards;

    private Random random;

    public Deck() {
        cards = new Stack<>();
        random = new Random();
    }

    public void initialize() {
        for (int i = 0; i < NUM_CARDS_OF_SINGLE_DECK; i++) {
            cards.push(i % (HIGHEST_CARD-1)+2);
        }
    }

    public void shuffle() {
        Collections.shuffle(cards, random);
    }

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

    public List<Integer> getAllCards() {
        return new ArrayList<>(cards);
    }

    public class EmptyDeckException extends Exception {
        public EmptyDeckException(String message) {
            super(message);
        }
    }
}
