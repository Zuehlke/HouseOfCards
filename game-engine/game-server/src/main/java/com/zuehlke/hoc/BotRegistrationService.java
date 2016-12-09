package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.of;

/**
 * Responsible for persisting URI and UUID of all registered players.
 *
 * @author Lukas Hofmaier
 */
@Component
public class BotRegistrationService implements RegistrationService {

    private Map<String, String> playerName2Uri = new HashMap<>();
    private Map<UUID, Player> uuid2playerName = new HashMap<>();

    public RegistrationInfoMessage register(RegisterMessage registerMessage, Player player) {
        String uri = String.format("http://%s:%d", registerMessage.getHostname(), registerMessage.getPort());
        playerName2Uri.put(registerMessage.getPlayerName(), uri);
        UUID uuid = UUID.randomUUID();
        uuid2playerName.put(uuid, player);

        return buildRegistrationInfoMessage(registerMessage, uuid);
    }

    private RegistrationInfoMessage buildRegistrationInfoMessage(RegisterMessage registerMessage, UUID uuid) {
        RegistrationInfoMessage registrationResponse = new RegistrationInfoMessage();
        registrationResponse.setPlayerName(registerMessage.getPlayerName());
        registrationResponse.setUUID(uuid);
        registrationResponse.setInfoMessage(RegistrationInfoMessage.Result.CONFIRMATION);
        return registrationResponse;
    }

    public boolean isRegistered(String playerName) {
        return playerName2Uri.containsKey(playerName);
    }

    public Optional<String> getUriByPlayerName(String playerName) {
        return playerName2Uri.get(playerName) != null ? of(playerName2Uri.get(playerName)) : Optional.empty();
    }

    public Optional<Player> getPlayerByUuid(UUID uuid) {
        return uuid2playerName.get(uuid) != null ? of(uuid2playerName.get(uuid)) : Optional.empty();
    }
}
