package com.zuehlke.hoc;


import com.zuehlke.hoc.Exceptions.InitGameException;
import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.NokerGameObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.of;


/**
 * This class represents a Noker game.
 * A new game is started as soon as the expected
 * number of players joined the game calling {@link #createPlayer(String)}.
 * A Noker game consists of multiple matches which are played until one player
 * owns the total amount of chips of the current game. Players place their moves
 * by calling {@link #playerSet(Player, long)} or {@link #playerFold(Player)}.
 */
public class NokerGame {

    private final NokerGameObserverAdapter notifier;
    private final List<Player> gamePlayers;

    private int expectedNumOfPlayers;
    private Match currentMatch;


    public NokerGame(int expectedNumOfPlayers, NokerGameObserverAdapter notifier) {
        this.notifier = new NokerGameObserverAdapter(notifier);
        if (expectedNumOfPlayers < NokerSettings.MIN_NUM_OF_PLAYERS) {
            throw new InitGameException(
                    String.format("A Noker game requires at least %d gamePlayers", NokerSettings.MIN_NUM_OF_PLAYERS));
        } else if (expectedNumOfPlayers > NokerSettings.MAX_NUM_OF_PLAYERS) {
            throw new InitGameException(
                    String.format("A Noker game can have a maximum of %d gamePlayers", NokerSettings.MAX_NUM_OF_PLAYERS));
        }
        this.expectedNumOfPlayers = expectedNumOfPlayers;
        this.gamePlayers = new ArrayList<>();
    }


    /**
     * Adds a player to the current game. If the expected number of
     * players joined, the game is started automatically.
     *
     * @param playerName name of the joining player
     * @return player object of the registered player
     */
    public Player createPlayer(String playerName) {
        Player player = new Player(playerName);
        gamePlayers.add(player);
        notifier.broadcastPlayerJoined(playerName);
        if (allPlayersJoined()) {
            startGame();
        }
        return player;
    }

    private boolean allPlayersJoined() {
        return gamePlayers.size() == expectedNumOfPlayers;
    }

    private void startGame() {
        gamePlayers.forEach(player -> player.setChipsStack(NokerSettings.INITIAL_CHIPS));
        notifier.broadcastGameStarted(gamePlayers);
        currentMatch = new Match(gamePlayers, notifier);
        currentMatch.startMatch();
    }

    public void playerFold(Player player) {
        currentMatch.playerFold(player);
        ifMatchIsFinishedGoAhead();
    }

    /**
     * This method summarizes both moves call and raise. The move
     * is handled according to the set amount of chips.
     *
     * @param player current player
     * @param chips  amount of chips to be set into the pot
     */
    public void playerSet(Player player, long chips) {
        currentMatch.playerSet(player, chips);
        ifMatchIsFinishedGoAhead();
    }

    private void ifMatchIsFinishedGoAhead() {
        if (currentMatch.isFinished()) {
            if (currentMatch.existsMoreThanOnePlayerWithChips()) {
                currentMatch = currentMatch.createNextMatch();
                currentMatch.startMatch();
            } else {
                notifier.broadcastGameFinished();
            }
        }
    }

    /**
     * Returns a player with the name defined by the argument. If the playerName is unknown the method return an empty
     * optional.
     *
     * @param playerName name of the demanded player.
     * @return an Optional that contains the player with the defined name.
     */
    public Optional<Player> getPlayer(String playerName) {
        List<Player> filterResult = gamePlayers.stream().filter(x -> x.getName().equals(playerName)).collect(Collectors.toList());
        return filterResult.size() == 1 ? of(filterResult.get(0)) : Optional.empty();
    }
}
