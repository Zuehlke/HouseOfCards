package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import com.zuehlke.hoc.rest.server2bot.RoundStartedMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.codehaus.jackson.map.ObjectMapper;

import static akka.pattern.Patterns.ask;

/**
 * @author Lukas Hofmaier
 */
public class RoundStartedProcessor implements Processor {

    private ActorRef httpReceiverActor;

    public RoundStartedProcessor(ActorRef httpReceiver){
        this.httpReceiverActor = httpReceiver;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        InputStreamCache inputStreamCache = (InputStreamCache) exchange.getIn().getBody();
        ObjectMapper objectMapper = new ObjectMapper();

        RoundStartedMessage roundStartedMessage = objectMapper.readValue(inputStreamCache, RoundStartedMessage.class);
        ask(this.httpReceiverActor, roundStartedMessage, 1000);

    }
}
