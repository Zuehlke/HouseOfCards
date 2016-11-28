package com.zuehlke.hoc.examplebot;

import akka.actor.UntypedActor;
import com.zuehlke.hoc.rest.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reacts on messages from the competition runner and decides the next move. This actor will register itself and
 * afterward it will send the move "call" if it has enough credit. When the game is over it will terminated the
 * application.
 *
 * @author Lukas Hofmaier
 */
public class JustCallActor extends UntypedActor {

    private final static Logger log = LoggerFactory.getLogger(JustCallActor.class);

    @Override
    public void onReceive(Object message) throws Throwable {
        if(message instanceof GameEvent){
            GameEvent gameEvent = (GameEvent)message;
            log.info("Received message: {}", gameEvent);

            getContext().stop(getSelf());
            getContext().system().terminate();
        }
    }
}
