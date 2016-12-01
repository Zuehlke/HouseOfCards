package com.zuehlke.hoc;


import com.zuehlke.hoc.Exceptions.InitGameException;
import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.NokerDeck;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.PlayerNotifier;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a Noker game.
 * To create a game, the number of players must be known.
 * A game takes registrations and moves from players and consists of
 * multiple matches. Matches are played until one player owns the total amount of chips of the game.
 */
public class NokerGame {

    public final static long INITIAL_CHIPS = 100;
    public final static int MIN_NUM_OF_PLAYERS = 2;
    public final static int CARDS_PER_PLAYER = 2;
    public static final int MAX_NUM_OF_PLAYERS = NokerDeck.NUM_CARDS_OF_SINGLE_DECK/CARDS_PER_PLAYER;

    private final PlayerNotifierAdapter notifier;
    private final List<Player> gamePlayers;

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

    public Player createPlayer(String playerName) {
        Player player = new Player(playerName);
        gamePlayers.add(player);
        if(allPlayersJoined()){
            startGame();
        }
        return player;
    }

    private boolean allPlayersJoined() {
        return gamePlayers.size() == numOfPlayers;
    }

    private void startGame() {
        notifier.broadcastGameStarted();
        gamePlayers.forEach(player -> player.setChipsStack(INITIAL_CHIPS));
        currentMatch = new Match(gamePlayers, new NokerDeck(),notifier);
        currentMatch.startMatch();
    }

    public void playerFold(Player player) {
        currentMatch.playerFold(player);
        ifMatchIsFinishedGoAhead();
    }

    /**
     * This method summarizes both moves call and raise.
     * @param player current player
     * @param chips  amount of chips to be set into the pot
     */
    public void playerSet(Player player, long chips) {
        currentMatch.playerSet(player, chips);
        ifMatchIsFinishedGoAhead();
    }

    private void ifMatchIsFinishedGoAhead() {
        if(currentMatch.isFinished()){
            if(currentMatch.hasMoreThanOnePlayerChips()){
                currentMatch = currentMatch.createNextMatch();
                currentMatch.startMatch();
            }else{
                notifier.broadcastGameFinished();
            }
        }
    }
}
