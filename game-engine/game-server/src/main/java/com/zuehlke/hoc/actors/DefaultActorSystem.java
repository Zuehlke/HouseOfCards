package com.zuehlke.hoc.actors;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import com.zuehlke.hoc.HelloService;
import com.zuehlke.hoc.RestBotNotifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultActorSystem {

    private static final String ACTOR_SYSTEM = "HouseOfCards";
    private static final String GAME_ENGINE_ACTOR = "GameEngine";

    private ActorSystem system;
    private IEngineActor gameEngine;

    @Autowired
    public DefaultActorSystem(HelloService helloService, BotNotifier botNotifier){
        this.system = ActorSystem.create(ACTOR_SYSTEM);
        this.gameEngine = createGameEngine(helloService,botNotifier);
    }

    private IEngineActor createGameEngine(HelloService helloService, BotNotifier botNotifier) {
        TypedProps<EngineActor> props = new TypedProps<>(IEngineActor.class,
                () -> new EngineActor(helloService, botNotifier));
        return TypedActor.get(system).typedActorOf(props, GAME_ENGINE_ACTOR);
    }

    public IEngineActor getGameEngine() {
        return gameEngine;
    }
}
