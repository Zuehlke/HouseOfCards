package com.zuehlke.houseofcards;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.japi.Creator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class DefaultActorSystem {

    public static final String ACTOR_SYSTEM = "HouseOfCards";

    @Autowired
    private HelloService helloService;


    private ActorSystem system;


    public DefaultActorSystem(){
        system = ActorSystem.create(ACTOR_SYSTEM);
    }

    public IEngineActor getGameEng() {

        TypedProps<EngineActor> props = new TypedProps<>(IEngineActor.class,
                () -> new EngineActor(helloService));
        return  TypedActor.get(system).typedActorOf(props);

    }
}
