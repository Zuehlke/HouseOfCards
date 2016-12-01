package com.zuehlke.hoc.actors;

import akka.actor.ActorSystem;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Lukas Hofmaier
 */
public class EngineActorTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void registerPlayer() throws Exception {

        // Mock<BotNotifier> botNotifierMock;

        ActorSystem system = ActorSystem.create();
        //   TypedProps<EngineActor> props = new TypedProps<>(IEngineActor.class, () -> new EngineActor(botNotifier, viewNotifier));
    }

}