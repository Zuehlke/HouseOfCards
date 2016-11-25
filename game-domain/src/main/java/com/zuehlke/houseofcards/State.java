package com.zuehlke.houseofcards;

import java.util.ArrayList;
import java.util.List;


public class State {

    private List<Player> registeredPlayers;             // all players that initially joined the table
    private List<Player> activePlayersInCurrentGame;    // players that still have chips in the current game
    private List<Player> activePlayersInCurrentMatch;   // players that haven't folded in the current match
    private Deck deck;
    private Player currentPlayer;
    private long pot = 0;
    private long raiseLimit = 0;

    public State() {
        registeredPlayers = new ArrayList<>();
        deck = new Deck();
        deck.initialize();
        deck.shuffle();
    }

    public List<Player> getRegisteredPlayers() {
        return registeredPlayers;
    }

    public void setRegisteredPlayers(List<Player> registeredPlayers) {
        this.registeredPlayers = registeredPlayers;
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

    public long getPot() {
        return pot;
    }

    public void setPot(long pot) {
        this.pot = pot;
    }

    public void increasePot(long amountOfChips) {
        pot += amountOfChips;
    }

    public void decreasePot(long amountOfChips) {
        pot -= amountOfChips;
    }

    public long getRaiseLimit() {
        return raiseLimit;
    }

    public void setRaiseLimit(long raiseLimit) {
        this.raiseLimit = raiseLimit;
    }

    @Override
    public String toString() {
        return "State{" +
                "registeredPlayers=" + registeredPlayers +
                ", currentPlayer=" + currentPlayer +
                ", pot=" + pot +
                '}';
    }
}
