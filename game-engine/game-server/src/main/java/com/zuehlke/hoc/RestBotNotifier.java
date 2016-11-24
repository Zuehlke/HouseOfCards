package com.zuehlke.hoc.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class BotNotifier {

    private static final Logger log = LoggerFactory.getLogger(EngineActor.class.getName());

    private final Map<String,String> bots = new HashMap<>();

    public void registerBot(String name, String callbackUrl){
        log.info("Register bot: " + name +"->"+callbackUrl);
        bots.put(name,callbackUrl);
        log.info("Current bots: " + bots.keySet().stream().reduce("", (a, b) -> a += (b + ", ")));

    }

    public void sendRegistrationOk(){

    }

}
