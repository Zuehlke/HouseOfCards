package com.zuehlke;

import java.util.List;


public class State {

    private List<Player> players;
    private Deck deck;
    private Player currentPlayer;
    private int currentRound = 0;
    private long pot = 0;

    public State(List<Player> players, Deck deck, Player currentPlayer, int currentRound, long pot) {
        this.players = players;
        this.deck = deck;
        this.currentPlayer = currentPlayer;
        this.currentRound = currentRound;
        this.pot = pot;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public long getPot() {
        return pot;
    }

    public void setPot(long pot) {
        this.pot = pot;
    }
}
