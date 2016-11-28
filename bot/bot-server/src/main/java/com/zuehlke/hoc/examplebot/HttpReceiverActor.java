package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import com.zuehlke.hoc.rest.GameEvent;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.apache.camel.converter.stream.InputStreamCache;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens for HTTP requests, transforms incoming requests to <code>RegistrationResponse</code> and forwards them to the
 * player actor
 *
 * @author Lukas Hofmaier
 */
public class HttpReceiverActor extends UntypedConsumerActor {

    private final static Logger log = LoggerFactory.getLogger(HttpReceiverActor.class);

    //the bot will reveive incomming HTTP request from the competition runner on this URI
    private String endpointUri;

    //reference to actor that reacts an game events sent by the competition server
    private ActorRef playerActor;

    public HttpReceiverActor(Credentials credentials, ActorRef playerActor) {
        this.endpointUri = String.format("jetty:http://%s:%d/start", credentials.getHostname(), credentials.getPort());
        log.debug("Receiver listens on {}", this.endpointUri);
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
            GameEvent gameEvent = objectMapper.readValue(inputStreamCache, GameEvent.class);
            log.debug("received event: {}", gameEvent.eventKind);

            this.playerActor.tell(gameEvent, getSelf());

            //return a message to the sender. This will return a HTTP response to the HTTP request sender
            // and close the TCP stream
            getSender().tell("aye, captain!", getSelf());
        }
    }
}
