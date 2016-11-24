package com.zuehlke.houseofcards.actors;

import com.zuehlke.houseofcards.HelloService;
import com.zuehlke.houseofcards.dto.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EngineActor implements IEngineActor {

    private static final Logger log = LoggerFactory.getLogger(EngineActor.class.getName());

    private final List<Bot> bots;

    private HelloService helloService;

    private int test;

    public EngineActor(HelloService helloService){
        this.helloService = helloService;
        this.bots = new ArrayList<>();
    }

    public void registerPlayer(Bot bot) {
        bots.add(bot);
    }


}