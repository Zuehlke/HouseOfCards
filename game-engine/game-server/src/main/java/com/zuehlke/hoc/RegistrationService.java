package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Responsible for persisting the bot uris and for mapping player names to uris.
 * @author Lukas Hofmaier
 */
public interface RegistrationService {

    /**
     * Stores URI and port of the bot in order to send messages to the bot given its name.
     *
     * @param registerMessage the registration message received from the bot.
     * @return true if the name was not already taken.
     */
    RegistrationInfoMessage register(RegisterMessage registerMessage, Player player);

    /**
     * Checks if a player with playerName is already registered.
     *
     * @param playerName player name
     * @return true if the player name is already registered.
     */
    boolean isRegistered(String playerName);

    /**
     * Return URI given a player name.
     *
     * @param playerName player name of the target.
     * @return URI of of the player.
     */
    Optional<String> getUriByPlayerName(String playerName);

    /**
     * Retrieve the player given an UUID
     *
     * @param uuid UUID set upon registration of the bot.
     * @return optional player
     */
    Optional<Player> getPlayerByUuid(UUID uuid);

    /**
     * Get uris of all registered bots.
     *
     * @return collection of all registered uris
     */
    Collection<String> getAllRegisteredUris();
}
