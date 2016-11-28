package com.zuehlke.hoc.actors;

import akka.actor.ActorContext;
import akka.actor.TypedActor;
import com.zuehlke.hoc.*;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EngineActor implements IEngineActor {

    private final static Logger log = LoggerFactory.getLogger(EngineActor.class);
    private final Game game;
    private final BotNotifier botNotifier;
    private final ViewNotifier viewNotifier;

    public EngineActor(BotNotifier botNotifier, ViewNotifier viewNotifier){
        // this.game = new NokerGame();
         this.botNotifier = botNotifier;
         this.viewNotifier = viewNotifier;
    }

    public void registerPlayer(String botName) {
        Player p = new Player(botName);
        game.addPlayer(p);
        log.info("Add player to game. Current number of players: {}. Min. number of players: {}", game.getPlayers().size(), NokerGame.MIN_NUM_OF_PLAYERS);
        if(game.isReady()){
            log.info("Start game");
            game.start();
            botNotifier.gameStartEvent();
            sendPlayerInfo();
        }
        
//        game.addPlayer(p);
//        if(game.isReady()){
//            game.start();
//            botNotifier.gameStartEvent();
//            sendPlayerInfo();
//        }

        viewNotifier.onRegisterPlayer(p);
    }

    private void sendPlayerInfo() {
        game.getPlayers().forEach(p -> botNotifier.sendPlayerInfo(p));
    }


}
