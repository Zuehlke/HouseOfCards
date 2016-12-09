package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import com.zuehlke.hoc.rest.server2bot.TurnRequestMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.codehaus.jackson.map.ObjectMapper;

import static akka.pattern.Patterns.ask;

/**
 * @author Lukas Hofmaier
 */
public class YourTurnProcessor implements Processor{

    private ActorRef httpReceiverActor;

    public YourTurnProcessor(ActorRef httpReceiver){
        this.httpReceiverActor = httpReceiver;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        InputStreamCache inputStreamCache = (InputStreamCache) exchange.getIn().getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        TurnRequestMessage roundStartedMessage = objectMapper.readValue(inputStreamCache, TurnRequestMessage.class);
        ask(this.httpReceiverActor, roundStartedMessage, 1000);
    }
}
