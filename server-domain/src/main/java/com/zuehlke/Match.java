package com.zuehlke;

import java.util.ArrayList;
import java.util.List;



public class Match {

    private List<Player> players;
    private Deck deck;
    private Player currentPlayer;
    private int currentRound = 0;
    private long pot = 0;

    /**
     * Init an new match.
     * @param players in the correct turn order
     */
    public Match(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
        deck.initialize();
        currentPlayer = players.get(0);
    }

    public void dealFirstCard() {
        players.forEach(p -> p.setFirstCard(deck.drawCard()));
    }

    public void shuffleDeck() {
        deck.shuffle();
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(players);
    }

    // TODO: implement state machine
}
