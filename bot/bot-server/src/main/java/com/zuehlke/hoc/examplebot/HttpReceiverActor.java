package com.zuehlke.hoc.examplebot;

import akka.actor.UntypedActor;

/**
 * Listens for HTTP requests, transforms incoming requests to <code>RegistrationResponse</code> and forwards them to the
 * player actor
 *
 * @author Lukas Hofmaier
 */
public class HttpReceiverActor extends UntypedActor {

    @Override
    public void onReceive(Object o) throws Throwable {

    }
}
