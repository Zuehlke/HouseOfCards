package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.PlayerInfo;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.RegisterMessage;

import java.util.ArrayList;
import java.util.List;

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
     * Broadcasts the game start to all bots
     */
    void gameStartEvent(List<PlayerInfo> players, PlayerInfo dealer);

    /**
     * Notify all bots still in the game about a new round.
     *
     * @param roundPlayers player that haven't send a fold in the last round.
     * @param roundNumber  number of the round
     * @param dealer       player in the role "dealer
     */
    void sendRoundStarted(List<PlayerInfo> roundPlayers, int roundNumber, PlayerInfo dealer);

    void sendPlayerInfo(Player player);

    /**
     * Informs the bot that it's registration message did not pass the paramater validation
     *
     * @param registerMessage the invalid RegisterMessage
     * @param errorMsg        A human readable error message.
     */
    void sendInvalidRegistrationMessage(RegisterMessage registerMessage, String errorMsg);

    /**
     * Invites a player to send its move and sends him a card.
     *
     * @param receiver             represented as string
     * @param minimalBet           minimal bet allowed at the current game state
     * @param maximalBet           maximal bet allowed at the current game state
     * @param amountOfCreditsInPot amount of money currently in the pot
     * @param card                 the card that is send to the player
     * @param activePlayers        a list of player that haven't send a fold in the current match.
     */
    void sendYourTurn(String receiver, long minimalBet, int maximalBet, int amountOfCreditsInPot, List<Integer> cards, ArrayList<PlayerInfo> activePlayers);
}
