package com.zuehlke.hoc.actors;

import akka.actor.ActorContext;
import akka.actor.TypedActor;
import com.zuehlke.hoc.*;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineActor implements IEngineActor {

    private final static Logger log = LoggerFactory.getLogger(EngineActor.class);
    private final Game game;
    private final BotNotifier botNotifier;
    private final ViewNotifier viewNotifier;

    public EngineActor(BotNotifier botNotifier, ViewNotifier viewNotifier){
        this.botNotifier = botNotifier;
        this.viewNotifier = viewNotifier;
        game = new NokerGame(3, new PlayerNotifier() {
            @Override
            public void sendCardInfo(String player, int card) {
                viewNotifier.sendGameInfo("Player "+player+" got card "+card);
            }

            @Override
            public void playersTurn(String player, long minimumChipsForCall) {

            }

            @Override
            public void broadcastGameStarts(StartInfo info) {

            }

            @Override
            public void broadcastPlayerRaised(String playerName, long amount) {

            }

            @Override
            public void broadcastPlayerCalled(String playerName) {

            }

            @Override
            public void broadcastPlayerFolded(String playerName) {

            }

            @Override
            public void broadcastNextRound() {

            }

            @Override
            public void broadcastNextMatch() {

            }
        });
    }

    public void registerPlayer(String botName) {
        Player player = game.createPlayer(botName);
        viewNotifier.sendGameInfo(player.getName()+" registered!");
        botNotifier.gameStartEvent();
        sendPlayerInfo();
    }

    private void sendPlayerInfo() {
        game.getPlayers().forEach(p -> botNotifier.sendPlayerInfo(p));
    }


}
