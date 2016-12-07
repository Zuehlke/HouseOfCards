package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.WebViewAndBotNotifier;
import com.zuehlke.hoc.rest.RegisterMessage;


public class EngineActor implements IEngineActor {

    private final NokerGame game;
    private final BotNotifier botNotifier;

    EngineActor(BotNotifier botNotifier, ViewNotifier viewNotifier) {
        this.botNotifier = botNotifier;
        WebViewAndBotNotifier playerNotifier = new WebViewAndBotNotifier(botNotifier, viewNotifier);
        this.game = new NokerGame(2, playerNotifier);
        playerNotifier.setGame(game);

    }

    public void registerPlayer(RegisterMessage registerMessage) {
        registerMessage.validate().ifPresent(errorMsg ->
                botNotifier.sendInvalidRegistrationMessage(registerMessage, errorMsg));

        if (this.botNotifier.registerBot(registerMessage)) {
            game.createPlayer(registerMessage.getPlayerName());
        }
    }
}
