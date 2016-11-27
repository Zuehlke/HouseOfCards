package com.zuehlke.hoc.examplebot;

import akka.actor.UntypedActor;

/**
 * Reacts on messages from the competition runner and decides the next move. This actor will register itself and
 * afterward it will send the move "call" if it has enough credit. When the game is over it will terminated the
 * application.
 *
 * @author Lukas Hofmaier
 */
public class JustCallActor extends UntypedActor {
    @Override
    public void onReceive(Object o) throws Throwable {

    }
}
