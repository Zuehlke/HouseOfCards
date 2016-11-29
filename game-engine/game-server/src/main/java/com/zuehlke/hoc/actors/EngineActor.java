package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.notification.api.PlayerNotifier;
import com.zuehlke.hoc.notification.api.StartInfo;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EngineActor implements IEngineActor {

    private final static Logger log = LoggerFactory.getLogger(EngineActor.class);
    private final NokerGame game;
    private final BotNotifier botNotifier;
    private final ViewNotifier viewNotifier;

    public EngineActor(BotNotifier botNotifier, ViewNotifier viewNotifier){
        this.botNotifier = botNotifier;
        this.viewNotifier = viewNotifier;
        game = new NokerGame(2, new PlayerNotifier() {
            @Override
            public void sendCardInfo(String player, int card) {
                log.info("Send card info");
                viewNotifier.sendGameInfo("Player "+player+" got card "+card);
            }

            @Override
            public void playersTurn(String player, long minimumChipsForCall) {
                viewNotifier.sendGameInfo("Next turn: Player "+player);

            }

            @Override
            public void broadcastGameStarts(StartInfo info) {
                log.info("Broadcast game start event to all registered players");
                viewNotifier.sendGameInfo("A new game started: " + info.toString());
                botNotifier.gameStartEvent();
            }

            @Override
            public void broadcastPlayerRaised(String playerName, long amount) {
                viewNotifier.sendGameInfo("Player " + playerName + " raised");

            }

            @Override
            public void broadcastPlayerCalled(String playerName) {
                viewNotifier.sendGameInfo("Player " + playerName + " called");


            }

            @Override
            public void broadcastPlayerFolded(String playerName) {
                viewNotifier.sendGameInfo("Player " + playerName + " folded");

            }

            @Override
            public void broadcastNextRound() {
                viewNotifier.sendGameInfo("New round started");

            }

            @Override
            public void broadcastNextMatch() {
                viewNotifier.sendGameInfo("New match started");

            }
        });
    }

    public void registerPlayer(RegisterMessage registerMessage) {
        if (this.botNotifier.registerBot(registerMessage)) {
            viewNotifier.sendGameInfo(registerMessage.getName() + " registered!");
            game.createPlayer(registerMessage.getName());
        }
    }

    private void sendPlayerInfo() {
        //game.getPlayers().forEach(p -> botNotifier.sendPlayerInfo(p));
    }


}
