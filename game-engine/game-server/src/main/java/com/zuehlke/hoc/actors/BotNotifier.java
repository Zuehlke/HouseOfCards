package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.PlayerInfo;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.RegisterMessage;

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
    void gameStartEvent(List<PlayerInfo> players, PlayerInfo deale);

    void sendPlayerInfo(Player player);

    /**
     * Informs the bot that it's registration message did not pass the paramater validation
     *
     * @param registerMessage the invalid RegisterMessage
     * @param errorMsg        A human readable error message.
     */
    void sendInvalidRegistrationMessage(RegisterMessage registerMessage, String errorMsg);

}
