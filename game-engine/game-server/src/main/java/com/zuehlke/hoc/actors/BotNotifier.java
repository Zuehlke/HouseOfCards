package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Defines the interface to the bots. Is used by <code>EngineActor</code> to send messages to the bots. Maps names to
 * bot addresses.
 *
 * @author Lukas Hofmaier
 */
public interface BotNotifier {

    /**
     * Stores URI and port of the bot in order to send messages to the bot given its name.
     *
     * @param registerMessage the registration message received from the bot.
     * @return true if the name was not already taken.
     */
    boolean registerBot(RegisterMessage registerMessage);

    /**
     * Sends <code>RegistrationInfoMessage</code> to defined player.
     *
     * @param registrationInfoMessage registration confirmation message
     */
    void sendRegistrationInfo(RegistrationInfoMessage registrationInfoMessage);

    /**
     * Retrieve the player name given an UUID
     *
     * @param playerUUID UUID set upon registration of the bot.
     * @return name of the player
     */
    Optional<String> getPlayerNameByUuid(UUID playerUUID);

    /**
     * Informs the bot that it's registration message did not pass the paramater validation
     *
     * @param registerMessage the invalid RegisterMessage
     * @param errorMsg        A human readable error message.
     */
    void sendInvalidRegistrationMessage(RegisterMessage registerMessage, String errorMsg);


    /**
     * Broadcasts the game start to all bots
     */
    void broadcastMatchStarted(List<Player> players, Player dealer);


    /**
     * Notify all bots still in the game about a new round.
     *
     * @param roundPlayers player that haven't send a fold in the last round.
     * @param roundNumber  number of the round
     * @param dealer       player in the role "dealer
     */
    void broadcastRoundStarted(List<Player> roundPlayers, int roundNumber, Player dealer);


    /**
     * Notify all bots about the player who folded.
     * @param playerName the player who placed a fold move
     */
    void broadcastPlayerFolded(String playerName);


    /**
     * Notify all bots about the player who set.
     * @param playerName the player who placed the set move
     * @param amount the amount of chips set
     */
    void broadcastPlayerSet(String playerName, long amount);


    /**
     * Invites a player to send its move and sends him a card.
     *
     * @param player               receiver of the request
     * @param lowerBound           minimal bet allowed at the current game state
     * @param upperBound           maximal bet allowed at the current game state
     * @param amountInPot          amount of money currently in the pot
     * @param activePlayers        a list of player that haven't send a fold in the current match.
     */
    void sendTurnRequest(Player player, long lowerBound, long upperBound, long amountInPot, List<Player> activePlayers);


    /**
     * Notify all the bots about the winners of the current match.
     *
     * @param matchWinners names of the winners of the current match
     */
    void broadcastMatchFinished(List<String> matchWinners);


    /**
     * Notify all the bots about the winner of the game.
     *
     * @param winnerName the winner of the game
     */
    void broadcastGameFinished(String winnerName);


    /**
     * Notify all the bots about the showdown of the current match.
     *
     * @param players remaining players in showdown phase of the current match
     */
    void broadcastShowdown(List<Player> players);
}
