package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.camel.CamelMessage;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;
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
class HttpReceiverActor extends UntypedActor {

    private final static Logger log = LoggerFactory.getLogger(HttpReceiverActor.class);

    //the bot will reveive incomming HTTP request from the competition runner on this URI
    private String endpointUri;

    //reference to actor that reacts an game events sent by the competition server
    private ActorRef playerActor;

    public HttpReceiverActor(Credentials credentials, ActorRef playerActor) {
        this.endpointUri = String.format("jetty:http://%s:%d/register_info", credentials.getHostname(), credentials.getPort());
        log.info("called ctor");
        log.debug("Receiver listens on {}", this.endpointUri);
        this.playerActor = playerActor;
    }

    /*
    @Override
    public String getEndpointUri() {
        return this.endpointUri;
    }
    */

    @Override
    public void onReceive(Object message) throws Throwable {
        //incoming HTTP request are sent as CamelMessage to the mailbox of this actor
        if (message instanceof CamelMessage) {
            CamelMessage camelMessage = (CamelMessage) message;
            log.info("received post request");
            //retrieve json from HTTP Request and convert it into a RegistrationResponse
            Object body = camelMessage.body();
            InputStreamCache inputStreamCache = (InputStreamCache) body;
            camelMessage.getHeaders().get("messagetype");

            ObjectMapper objectMapper = new ObjectMapper();
            //// TODO: 29.11.2016 handle exceptions occurring upon ObjectMapper:readValue
            RegistrationInfoMessage gameEvent = objectMapper.readValue(inputStreamCache, RegistrationInfoMessage.class);

            this.playerActor.tell(gameEvent, getSelf());

            //return a message to the sender. This will return a HTTP response to the HTTP request sender
            // and close the TCP stream
            getSender().tell("aye, captain!", getSelf());
        }
        if(message instanceof RegistrationInfoMessage){
            RegistrationInfoMessage registrationResponse = (RegistrationInfoMessage)message;
            log.info("received registration response");
        }
    }
}
