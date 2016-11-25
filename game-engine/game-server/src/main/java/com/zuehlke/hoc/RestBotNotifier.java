package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.actors.EngineActor;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RestBotNotifier implements BotNotifier{

    private static final Logger log = LoggerFactory.getLogger(RestBotNotifier.class.getName());

    private final Map<String,RegisterMessage> bots = new HashMap<>();

    public void registerBot(RegisterMessage registerMessage){
        log.info("Register bot: " + registerMessage.getName() +" -> "+registerMessage.getHostname()+":"+registerMessage.getPort());
        bots.put(registerMessage.getHostname(),registerMessage);
        log.info("Current bots: " + bots.keySet().stream().reduce("", (a, b) -> a += (b + ", ")));
    }

    public void gameStartEvent(){
        log.debug("Send start event to all bots.");
    }

    @Override
    public void sendPlayerInfo(Player player) {
        System.out.println("Send cards to '"+player.getName()+"':"+player.getFirstCard()+","+player.getSecondCard());
    }
}
