package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import com.zuehlke.hoc.rest.server2bot.MatchFinishedMessage;
import com.zuehlke.hoc.rest.server2bot.MatchStartedMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static akka.pattern.Patterns.ask;

public class MatchFinishedProcessor implements Processor {

    private final static Logger log = LoggerFactory.getLogger(RegisterInfoProcessor.class);
    private ActorRef httpReceiverActor;

    public MatchFinishedProcessor(ActorRef httpReceiver) {
        this.httpReceiverActor = httpReceiver;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("match_finished");

        InputStreamCache inputStreamCache = (InputStreamCache) exchange.getIn().getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        MatchFinishedMessage matchFinishedMessage = objectMapper.readValue(inputStreamCache, MatchFinishedMessage.class);
        ask(this.httpReceiverActor, matchFinishedMessage, 1000);
    }
}
