package com.zuehlke.hoc.examplebot;

import akka.actor.ActorRef;
import static akka.pattern.Patterns.ask;
import com.zuehlke.hoc.rest.server2bot.RegistrationInfoMessage;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.converter.stream.InputStreamCache;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Lukas Hofmaier
 */
public class RegisterInfoProcessor implements Processor {

    private final static Logger log = LoggerFactory.getLogger(RegisterInfoProcessor.class);

    private ActorRef httpReceiverActor;

    public RegisterInfoProcessor(ActorRef httpReceiverActor){
        this.httpReceiverActor = httpReceiverActor;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("received register_info");
        exchange.getIn().setHeader("messagetype", "registerinfo");
        Message msg = exchange.getIn();
        Object body = msg.getBody();

        InputStreamCache inputStreamCache = (InputStreamCache) body;
       // camelMessage.getHeaders().get("messagetype");

        ObjectMapper objectMapper = new ObjectMapper();
        //// TODO: 29.11.2016 handle exceptions occurring upon ObjectMapper:readValue
        RegistrationInfoMessage registrationResponse = objectMapper.readValue(inputStreamCache, RegistrationInfoMessage.class);
        log.info("received registration. player: {}", registrationResponse.getPlayerName());
        ask(this.httpReceiverActor, registrationResponse, 1000);

    }
}
