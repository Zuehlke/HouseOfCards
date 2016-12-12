package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.RegistrationService;
import com.zuehlke.hoc.WebViewAndBotNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.bot2server.FoldMessage;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.bot2server.SetMessage;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class EngineActor implements IEngineActor {

    private static final int NUM_OF_GAME_PLAYERS = 2;

    private static final Logger log = LoggerFactory.getLogger(EngineActor.class.getName());

    private final NokerGame game;
    private final BotNotifier botNotifier;
    private final RegistrationService botRegistrationService;

    EngineActor(BotNotifier botNotifier, ViewNotifier viewNotifier, RegistrationService botRegistrationService) {
        this.botNotifier = botNotifier;
        this.botRegistrationService = botRegistrationService;
        WebViewAndBotNotifier playerNotifier = new WebViewAndBotNotifier(botNotifier, viewNotifier);
        this.game = new NokerGame(NUM_OF_GAME_PLAYERS, playerNotifier);
    }

    public void registerPlayer(RegisterMessage registerMessage) {
        Optional<String> validationResult = validateRegistrationMessage(registerMessage);
        if (validationResult.isPresent()) {
            botNotifier.sendInvalidRegistrationMessage(registerMessage, validationResult.get());
        } else {
            Player newPlayer = game.createPlayer(registerMessage.getPlayerName());
            RegistrationInfoMessage registrationInfoMessage = this.botRegistrationService.register(registerMessage, newPlayer);
            this.botNotifier.sendRegistrationInfo(registrationInfoMessage);
            log.info("Registered player {}", registerMessage.getPlayerName());
        }
    }

    @Override
    public void fold(FoldMessage foldMessage) {
        Optional<Player> playerOptional = this.botRegistrationService.getPlayerByUuid(foldMessage.getUuid());
        playerOptional.ifPresent(game::playerFold);
    }

    @Override
    public void setBet(SetMessage setMessage) {
        Optional<Player> playerOptional = this.botRegistrationService.getPlayerByUuid(setMessage.getUuid());
        playerOptional.ifPresent(player -> {
            game.playerSet(player, setMessage.getAmount());
        });
    }


    /**
     * Validates a registration message.
     *
     * @param registerMessage the received registration message from a player
     * @return empty Optional if successful, else error message
     */
    private Optional<String> validateRegistrationMessage(RegisterMessage registerMessage) {
        Optional<String> validationResult;
        String playerName = registerMessage.getPlayerName();

        validationResult = registerMessage.validate();
        if (validationResult.isPresent()) {
            return validationResult;
        }

        if (botRegistrationService.isRegistered(playerName)) {
            validationResult = Optional.of(String.format("Error: Player with name %s already registered", playerName));
        }
        return validationResult;
    }
}
