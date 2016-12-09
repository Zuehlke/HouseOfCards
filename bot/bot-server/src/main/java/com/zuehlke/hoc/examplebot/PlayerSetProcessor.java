package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import com.zuehlke.hoc.rest.server2bot.FoldMessage;
import com.zuehlke.hoc.rest.server2bot.SetMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static akka.pattern.Patterns.ask;

public class PlayerSetProcessor implements Processor {

    private final static Logger log = LoggerFactory.getLogger(RegisterInfoProcessor.class);
    private ActorRef httpReceiverActor;

    public PlayerSetProcessor(ActorRef httpReceiver) {
        this.httpReceiverActor = httpReceiver;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Processed player_set");

        InputStreamCache inputStreamCache = (InputStreamCache) exchange.getIn().getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        SetMessage setMessage = objectMapper.readValue(inputStreamCache, SetMessage.class);
        ask(this.httpReceiverActor, setMessage, 1000);
    }
}
