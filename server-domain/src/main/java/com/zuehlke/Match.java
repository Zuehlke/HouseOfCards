package com.zuehlke;

import java.util.ArrayList;
import java.util.List;



public class Match {

    private State state;

    /**
     * Init an new match.
     * @param players already in the correct order of turns
     */
    public Match(List<Player> players, Deck deck) {
        deck.initialize();
        deck.shuffle();
        state = new State(players, deck, players.get(0), 0, 0);
    }

    public void dealFirstCard() {
        state.getPlayers().forEach(p -> p.setFirstCard(state.getDeck().drawCard()));
    }

    public Player getCurrentPlayer() {
        return state.getCurrentPlayer();
    }

    public void setCurrentPlayer(Player currentPlayer) {
        state.setCurrentPlayer(currentPlayer);
    }

    public int getCurrentRound() {
        return state.getCurrentRound();
    }

    public void setCurrentRound(int currentRound) {
        state.setCurrentRound(currentRound);
    }

    public long getPot() {
        return state.getPot();
    }

    public void setPot(long pot) {
        state.setPot(pot);
    }

    public void incrementPot(long amountOfChips) {
        state.setPot(state.getPot() + amountOfChips);
    }

    public void decrementPot(long amountOfChips) {
        state.setPot(state.getPot() - amountOfChips);
    }

    public Deck getDeck() {
        return state.getDeck();
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(state.getPlayers());
    }

}
