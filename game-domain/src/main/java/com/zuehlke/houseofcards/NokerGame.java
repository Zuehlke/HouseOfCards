package com.zuehlke.houseofcards;


import com.zuehlke.houseofcards.Exceptions.InitGameException;
import com.zuehlke.houseofcards.model.Match;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is used for testing the communication between the
 * external caller and the domain. Whenever a method is called
 * a mocked state is returned.
 */
public class NokerGame {

    public final static long INITIAL_CHIPS = 100;
    public final static int MIN_NUM_OF_PLAYERS = 2;
    public final static int CARDS_PER_PLAYER = 2;
    public static final int MAX_NUM_OF_PLAYERS = Deck.NUM_CARDS_OF_SINGLE_DECK/CARDS_PER_PLAYER;

    private final PlayerNotifierAdapter notifier;

    private List<Player> gamePlayers;
    private int numOfPlayers;
    private Match currentMatch;


    public NokerGame(int numOfPlayers, PlayerNotifier notifier) {
        this.notifier = new PlayerNotifierAdapter(notifier);
        if (numOfPlayers < MIN_NUM_OF_PLAYERS) {
            throw new InitGameException(
                    String.format("A Noker game requires at least %d gamePlayers", MIN_NUM_OF_PLAYERS));
        } else if (numOfPlayers > MAX_NUM_OF_PLAYERS) {
            throw new InitGameException(
                    String.format("A Noker game can have a maximum of %d gamePlayers", MAX_NUM_OF_PLAYERS));
        }
        this.numOfPlayers = numOfPlayers;
        this.gamePlayers = new ArrayList<>();
    }

    public void createPlayer(String playerName) {
        Player player = new Player(playerName);
        gamePlayers.add(player);
        if(allPlayersJoined()){
            startGame();
        }
    }

    public boolean allPlayersJoined() {
        return gamePlayers.size() == numOfPlayers;
    }


    public void startGame() {
        gamePlayers.forEach(player -> player.setChipsStack(INITIAL_CHIPS));
        currentMatch = new Match(gamePlayers, notifier);
        notifier.publishStart(currentMatch);
        currentMatch.startMatch();
    }

    public void handleMove(Move move) {
        /*
        // TODO: check if round/match/game finished
        if (move.isValid(gameState)) {
            move.execute(gameState);
        }
        // TODO: handle next round/match etc.
        return gameState;*/
    }
}
