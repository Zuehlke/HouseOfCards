package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.WebViewAndBotNotifier;
import com.zuehlke.hoc.rest.FoldMessage;
import com.zuehlke.hoc.rest.RegisterMessage;
import com.zuehlke.hoc.rest.SetMessage;

import java.util.Optional;


public class EngineActor implements IEngineActor {

    private static final int NUM_OF_GAME_PLAYERS = 2;

    private final NokerGame game;
    private final BotNotifier botNotifier;


    EngineActor(BotNotifier botNotifier, ViewNotifier viewNotifier) {
        this.botNotifier = botNotifier;
        WebViewAndBotNotifier playerNotifier = new WebViewAndBotNotifier(botNotifier, viewNotifier);
        this.game = new NokerGame(NUM_OF_GAME_PLAYERS, playerNotifier);
    }

    public void registerPlayer(RegisterMessage registerMessage) {
        registerMessage.validate().ifPresent(errorMsg ->
                botNotifier.sendInvalidRegistrationMessage(registerMessage, errorMsg));

        if (this.botNotifier.registerBot(registerMessage)) {
            game.createPlayer(registerMessage.getPlayerName());
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
