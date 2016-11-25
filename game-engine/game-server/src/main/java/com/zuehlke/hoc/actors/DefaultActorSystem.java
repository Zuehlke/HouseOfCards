package com.zuehlke.hoc.actors;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultActorSystem {

    private static final String ACTOR_SYSTEM = "HouseOfCards";
    private static final String GAME_ENGINE_ACTOR = "GameEngine";

    private ActorSystem system;
    private IEngineActor gameEngine;

    @Autowired
    public DefaultActorSystem(BotNotifier botNotifier, ViewNotifier viewNotifier){
        this.system = ActorSystem.create(ACTOR_SYSTEM);
        this.gameEngine = createGameEngine(botNotifier, viewNotifier);
    }

    private IEngineActor createGameEngine(BotNotifier botNotifier, ViewNotifier viewNotifier) {
        TypedProps<EngineActor> props = new TypedProps<>(IEngineActor.class,
                () -> new EngineActor(botNotifier, viewNotifier));
        return TypedActor.get(system).typedActorOf(props, GAME_ENGINE_ACTOR);
    }

    public IEngineActor getGameEngine() {
        return gameEngine;
    }
}
