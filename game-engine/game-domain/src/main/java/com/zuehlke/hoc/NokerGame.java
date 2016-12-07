package com.zuehlke.hoc;


import com.zuehlke.hoc.Exceptions.InitGameException;
import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.NokerDeck;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.PlayerNotifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.of;


/**
 * This class represents a Noker game.
 * A new game takes the number of players as argument. As soon as the expected
 * number of payers joined the game calling {@link #createPlayer(String)} ()},
 * the game is started.
 * A Noker game consists of multiple matches which are played until one player
 * owns the total amount of chips of the current game. Players place their moves
 * by calling {@link #playerSet(Player, long)} ()} or {@link #playerFold(Player)} ()}.
 */
public class NokerGame {

    public final static long INITIAL_CHIPS = 100;
    public final static int MIN_NUM_OF_PLAYERS = 2;
    public final static int CARDS_PER_PLAYER = 2;
    public static final int MAX_NUM_OF_PLAYERS = NokerDeck.NUM_CARDS_OF_SINGLE_DECK / CARDS_PER_PLAYER;

    private final PlayerNotifierAdapter notifier;
    private final List<Player> gamePlayers;

    private int expectedNumOfPlayers;
    private Match currentMatch;


    public NokerGame(int expectedNumOfPlayers, PlayerNotifier notifier) {
        this.notifier = new PlayerNotifierAdapter(notifier);
        if (expectedNumOfPlayers < MIN_NUM_OF_PLAYERS) {
            throw new InitGameException(
                    String.format("A Noker game requires at least %d gamePlayers", MIN_NUM_OF_PLAYERS));
        } else if (expectedNumOfPlayers > MAX_NUM_OF_PLAYERS) {
            throw new InitGameException(
                    String.format("A Noker game can have a maximum of %d gamePlayers", MAX_NUM_OF_PLAYERS));
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
        if (allPlayersJoined()) {
            startGame();
        }
        return player;
    }

    private boolean allPlayersJoined() {
        return gamePlayers.size() == expectedNumOfPlayers;
    }

    private void startGame() {
        notifier.broadcastGameStarted();
        gamePlayers.forEach(player -> player.setChipsStack(INITIAL_CHIPS));
        currentMatch = new Match(gamePlayers, new NokerDeck(), notifier);
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

    /**
     * Returns a player with the name defined by the argument. If the playername is unknown the method return an empty
     * optional.
     *
     * @param playername name of the demanded player.
     * @return an Optional that contains the player with the defined name.
     */
    public Optional<Player> getPlayer(String playername) {
        List<Player> filterResult = gamePlayers.stream().filter(x -> x.getName().equals(playername)).collect(Collectors.toList());
        return filterResult.size() == 1 ? of(filterResult.get(0)) : Optional.empty();
    }

    private void ifMatchIsFinishedGoAhead() {
        if (currentMatch.isFinished()) {
            if (currentMatch.hasMoreThanOnePlayerChips()) {
                currentMatch = currentMatch.createNextMatch();
                currentMatch.startMatch();
            } else {
                notifier.broadcastGameFinished();
            }
        }
    }
}
