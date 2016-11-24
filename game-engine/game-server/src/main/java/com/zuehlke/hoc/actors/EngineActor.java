package com.zuehlke.hoc.actors;

import akka.actor.ActorContext;
import akka.actor.TypedActor;
import com.zuehlke.hoc.HelloService;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EngineActor implements IEngineActor {

    private static final Logger log = LoggerFactory.getLogger(EngineActor.class.getName());

    private final List<String> bots;

    public EngineActor(HelloService helloService){
         this.bots = new ArrayList<>();
    }

    public void registerPlayer(RegisterMessage bot) {
        bots.add(bot.getName());

        log.info("Current bots: " + bots.stream().reduce("", (a, b) -> a += (b + ", ")));
/*
        helloService.registerReply();
         = new RestTemplate();
        String s = response.postForObject(bot.getHostname(), bot, String.class);
*/
    }


}
