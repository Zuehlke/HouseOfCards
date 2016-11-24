package com.zuehlke.hoc.actors;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import com.zuehlke.hoc.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultActorSystem {

    private static final String ACTOR_SYSTEM = "HouseOfCards";
    private static final String GAME_ENGINE_ACTOR = "GameEngine";

    private HelloService helloService;

    private ActorSystem system;
    private IEngineActor gameEngine;

    public DefaultActorSystem(@Autowired HelloService helloService){
        this.helloService = helloService;
        this.system = ActorSystem.create(ACTOR_SYSTEM);
        this.gameEngine = createGameEngine();
    }

    private IEngineActor createGameEngine() {
        TypedProps<EngineActor> props = new TypedProps<>(IEngineActor.class,
                () -> new EngineActor(helloService));
        return TypedActor.get(system).typedActorOf(props, GAME_ENGINE_ACTOR);
    }

    public IEngineActor getGameEngine() {
        return gameEngine;
    }
}
