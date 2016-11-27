package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import com.zuehlke.hoc.rest.RegistrationResponse;
import org.apache.camel.converter.stream.InputStreamCache;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Listens for HTTP requests, transforms incoming requests to <code>RegistrationResponse</code> and forwards them to the
 * player actor
 *
 * @author Lukas Hofmaier
 */
public class HttpReceiverActor extends UntypedConsumerActor {

    //the bot will reveive incomming HTTP request from the competition runner on this URI
    private String endpointUri;

    //reference to actor that reacts an game events sent by the competition server
    private ActorRef playerActor;

    public HttpReceiverActor(String listenOnUri, ActorRef playerActor) {
        this.endpointUri = String.format("jetty:%s", listenOnUri);
        this.playerActor = playerActor;
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

            //retrieve json from HTTP Request and convert it into a RegistrationResponse
            Object body = camelMessage.body();
            InputStreamCache inputStreamCache = (InputStreamCache) body;
            ObjectMapper objectMapper = new ObjectMapper();
            RegistrationResponse registrationResponse = objectMapper.readValue(inputStreamCache, RegistrationResponse.class);

            this.playerActor.tell(registrationResponse, getSelf());

            //return a message to the sender. This will return a HTTP response to the HTTP request sender
            // and close the TCP stream
            getSender().tell("aye, captain!", getSelf());
        }
    }
}
