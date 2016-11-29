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
public class RestBotNotifier implements BotNotifier {

    private static final Logger log = LoggerFactory.getLogger(RestBotNotifier.class.getName());

    private final Map<String, RegisterMessage> bots = new HashMap<>();
    private final RestTemplate restTemplate;

    @Autowired
    public RestBotNotifier(RestTemplateBuilder restBuilder) {
        restTemplate = restBuilder.build();
    }

    /**
     * Stores the URI and the port for a given player to send him game event messages.
     *
     * @param registerMessage RegistrationMessage containing name, uri and port of the sender.
     * @return Returns true if the registrations was successful, otherwise false.
     */
    @Override
    public boolean registerBot(RegisterMessage registerMessage) {
        String url = String.format("http://%s:%d/start", registerMessage.getHostname(), registerMessage.getPort());
        GameEvent registrationResponse = new GameEvent();
        if (bots.containsKey(registerMessage.getName())) {
            log.info(String.format("Name %s is already taken. Send NAME_ALREADY_TAKEN message to originator", registerMessage.getName()));
            registrationResponse.setEventKind(GameEvent.EventKind.NAME_ALREADY_TAKEN);
            restTemplate.postForObject(url, registrationResponse, String.class);
            return false;
        }
        log.info("Register bot: " + registerMessage.getName() + " -> " + registerMessage.getHostname() + ":" + registerMessage.getPort());
        bots.put(registerMessage.getName(), registerMessage);
        log.info("Current bots: " + bots.keySet().stream().reduce("", (a, b) -> a += (b + ", ")));
        registrationResponse.setEventKind(GameEvent.EventKind.CONFIRMATION);
        restTemplate.postForObject(url, registrationResponse, String.class);
        return true;
    }

    @Override
    public void gameStartEvent() {
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
