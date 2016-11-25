package com.zuehlke.hoc.examplebot;

import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;

/**
 * Listens for HTTP requests, transforms incoming requests to <code>RegistrationResponse</code> and forwards them to the
 * player actor
 *
 * @author Lukas Hofmaier
 */
public class HttpReceiverActor extends UntypedConsumerActor {

    //the bot will reveive incomming HTTP request from the competition runner on this URI
    private String endpointUri;

    public HttpReceiverActor(String listenOnUri) {
        this.endpointUri = String.format("jetty:%1", listenOnUri);
    }

    @Override
    public String getEndpointUri() {
        return this.endpointUri;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        //incomming HTTP request are sent as CamelMessage to the mailbox of this actor
        if (message instanceof CamelMessage) {
            CamelMessage camelMessage = (CamelMessage) message;

            //return a message to the sender. This will return a HTTP response to the HTTP request sender
            // and close the TCP stream
            getSender().tell("aye, captain!", getSelf());
        }
    }
}
