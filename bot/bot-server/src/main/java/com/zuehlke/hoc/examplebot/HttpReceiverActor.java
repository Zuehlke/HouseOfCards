package com.zuehlke.hoc.examplebot;

import akka.camel.javaapi.UntypedConsumerActor;

/**
 * Listens for HTTP requests, transforms incoming requests to <code>RegistrationResponse</code> and forwards them to the
 * player actor
 *
 * @author Lukas Hofmaier
 */
public class HttpReceiverActor extends UntypedConsumerActor {

    @Override
    public String getEndpointUri() {
        return null;
    }

    @Override
    public void onReceive(Object o) throws Throwable {

    }
}
