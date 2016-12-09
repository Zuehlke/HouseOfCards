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
        log.info("registerPlayer");
        //validate register message
        registerMessage.validate().ifPresent(errorMsg ->
                botNotifier.sendInvalidRegistrationMessage(registerMessage, errorMsg));

        if (!this.botRegistrationService.isRegistered(registerMessage.getPlayerName())) {
            log.info("add player to game");
            Player newPlayer = game.createPlayer(registerMessage.getPlayerName());
            RegistrationInfoMessage registrationInfoMessage = this.botRegistrationService.register(registerMessage, newPlayer);
            this.botNotifier.sendRegistrationInfo(registrationInfoMessage);
        }
    }

    @Override
    public void fold(FoldMessage foldMessage) {
        Optional<String> playerName = botNotifier.getPlayerNameByUuid(foldMessage.getUuid());
        playerName.flatMap(game::getPlayer).ifPresent(game::playerFold);
    }

    @Override
    public void setBet(SetMessage setMessage) {
        Optional<String> playerNameOptional = this.botNotifier.getPlayerNameByUuid(setMessage.getUuid());
        playerNameOptional.flatMap(this.game::getPlayer).ifPresent(player -> {
            game.playerSet(player, setMessage.getAmount());
        });
    }
}
