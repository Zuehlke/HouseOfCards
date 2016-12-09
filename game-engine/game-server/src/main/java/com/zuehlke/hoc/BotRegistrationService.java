package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
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
public class BotRegistrationService {

    private Map<String, String> playerName2Uri = new HashMap<>();
    private Map<UUID, Player> uuid2playerName = new HashMap<>();

    public void register(RegisterMessage registerMessage, Player player) {
        String uri = String.format("http://%s:%d", registerMessage.getHostname(), registerMessage.getPort());
        playerName2Uri.put(registerMessage.getPlayerName(), uri);
        uuid2playerName.put(UUID.randomUUID(), player);
    }

    public Optional<String> getUriByPlayerName(String playerName) {
        return playerName2Uri.get(playerName) != null ? of(playerName2Uri.get(playerName)) : Optional.empty();
    }

    public Optional<Player> getPlayerByUuid(UUID uuid) {
        return uuid2playerName.get(uuid) != null ? of(uuid2playerName.get(uuid)) : Optional.empty();
    }
}
