package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import com.zuehlke.hoc.rest.server2bot.MatchStartedMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static akka.pattern.Patterns.ask;

/**
 * @author Lukas Hofmaier
 */
public class MatchStartedProcessor implements Processor{

    private final static Logger log = LoggerFactory.getLogger(RegisterInfoProcessor.class);
    private ActorRef httpReceiverActor;

    public MatchStartedProcessor(ActorRef httpReceiver){
        this.httpReceiverActor = httpReceiver;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("match_started");

        InputStreamCache inputStreamCache = (InputStreamCache) exchange.getIn().getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        MatchStartedMessage registrationResponse = objectMapper.readValue(inputStreamCache, MatchStartedMessage.class);
        ask(this.httpReceiverActor, registrationResponse, 1000);
    }
}
