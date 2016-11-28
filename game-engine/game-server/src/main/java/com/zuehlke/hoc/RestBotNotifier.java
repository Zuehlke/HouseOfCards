package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.GameEvent;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class RestBotNotifier implements BotNotifier{

    private static final Logger log = LoggerFactory.getLogger(RestBotNotifier.class.getName());

    private final Map<String,RegisterMessage> bots = new HashMap<>();
    private final RestTemplate restTemplate;

    @Autowired
    public RestBotNotifier(RestTemplateBuilder restBuilder) {
        restTemplate = restBuilder.build();
    }

    @Override
    public void registerBot(RegisterMessage registerMessage){
        log.info("Register bot: " + registerMessage.getName() +" -> "+registerMessage.getHostname()+":"+registerMessage.getPort());
        bots.put(registerMessage.getName(), registerMessage);
        log.info("Current bots: " + bots.keySet().stream().reduce("", (a, b) -> a += (b + ", ")));
    }

    @Override
    public void gameStartEvent(){
        log.debug("Send start event to all bots.");

        bots.values().stream().forEach(x -> {
            String url = String.format("http://%s:%d/start", x.getHostname(), x.getPort());
            log.info("send game start to {}", url);
            GameEvent startEvent = new GameEvent();
            startEvent.setEventKind(GameEvent.EventKind.START);
            restTemplate.postForObject(url, startEvent, String.class);
        });
    }

    @Override
    public void sendPlayerInfo(Player player) {
        RegisterMessage m = bots.get(player.getName());
        if (m != null) {
            String response = restTemplate.postForObject(String.format("http://%s:%d/update", m.getHostname(), m.getPort()),
                    player,
                    String.class);

            System.out.println("Send cards to '" + player.getName() + "':" + player.getFirstCard() + "," + player.getSecondCard());
        }
    }
}
