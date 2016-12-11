package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Lukas Hofmaier
 */
public interface RegistrationService {

    RegistrationInfoMessage register(RegisterMessage registerMessage, Player player);

    boolean isRegistered(String playerName);

    Optional<String> getUriByPlayerName(String playerName);

    /**
     * Retrieve the player given an UUID
     *
     * @param uuid UUID set upon registration of the bot.
     * @return optional player
     */
    Optional<Player> getPlayerByUuid(UUID uuid);
}
