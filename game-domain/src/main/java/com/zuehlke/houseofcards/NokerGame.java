package com.zuehlke.houseofcards;


import java.util.List;


/**
 * This class is used for testing the communication between the
 * external caller and the domain. Whenever a method is called
 * a mocked state is returned.
 */
public class NokerGame implements Game {

    public final static long INITIAL_CHIPS = 100;
    public final static int MIN_NUM_OF_PLAYERS = 2;
    public final static int CARDS_PER_PLAYER = 2;
    public static final int MAX_NUM_OF_PLAYERS = Deck.NUM_CARDS_OF_SINGLE_DECK/CARDS_PER_PLAYER;

    private int numOfPlayers;
    private boolean isReady;
    private State gameState;


    public NokerGame(int numOfPlayers) {
        gameState = new State();
        this.numOfPlayers = numOfPlayers;
        isReady = false;    // TODO: move to state class?
    }

    @Override
    public State addPlayer(Player player) {
        // TODO: fix
        List<Player> players = gameState.getRegisteredPlayers();
        if (players.size() == numOfPlayers) {
            start();
        } else {
            players.add(player);
        }
        return gameState;
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    private void start() {
        gameState.setPot(0);
        gameState.setCurrentPlayer(gameState.getRegisteredPlayers().get(0));  // TODO: rotate players on each match
        gameState.getRegisteredPlayers().forEach(p -> p.setChipsStack(INITIAL_CHIPS));
        gameState.getRegisteredPlayers().forEach(p -> p.setFirstCard(gameState.getDeck().drawCard()));
        isReady = true;
    }

    @Override
    public State handleMove(Move move) {
        // TODO: check if round/match/game finished
        if (move.isValid(gameState)) {
            move.execute(gameState);
        }
        // TODO: handle next round/match etc.
        return gameState;
    }
}
