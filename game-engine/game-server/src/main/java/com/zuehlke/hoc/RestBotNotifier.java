package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.GameEvent;
import com.zuehlke.hoc.rest.MatchStartedMessage;
import com.zuehlke.hoc.rest.PlayerDTO;
import com.zuehlke.hoc.rest.RegisterMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if (bots.containsKey(registerMessage.getPlayerName())) {
            log.info("Name {} is already taken.", registerMessage.getPlayerName());
            registrationResponse.setEventKind(GameEvent.EventKind.NAME_ALREADY_TAKEN);
            log.info("Send NAME_ALREADY_TAKEN message to {}.", url);
            restTemplate.postForObject(url, registrationResponse, String.class);
            return false;
        }
        log.info("Register bot: " + registerMessage.getPlayerName() + " -> " + registerMessage.getHostname() + ":" + registerMessage.getPort());
        bots.put(registerMessage.getPlayerName(), registerMessage);
        log.info("Current bots: " + bots.keySet().stream().reduce("", (a, b) -> a += (b + ", ")));
        registrationResponse.setEventKind(GameEvent.EventKind.RESERVATION_CONFIRMATION);
        log.info("Send RESERVATION_CONFIRMATION message for team {} to {}", registerMessage.getPlayerName(), url);
        restTemplate.postForObject(url, registrationResponse, String.class);
        return true;
    }

    @Override
    public void gameStartEvent(List<PlayerInfo> players, PlayerInfo dealer) {
        log.debug("Send match started event to all bots.");

        List<PlayerDTO> matchPlayers = players.stream().map(x -> new PlayerDTO(x.getName(), x.getChipstack())).collect(Collectors.toList());

        PlayerDTO dealerDto = new PlayerDTO(dealer.getName(), dealer.getChipstack());

        matchPlayers.stream().forEach(x -> {
            RegisterMessage registerMessage = bots.get(x.getName());
            if (registerMessage == null) {
                log.info("Player {} cannot be associated with a URI", x.getName());
            } else {
                String url = String.format("http://%s:%d/match_started", registerMessage.getHostname(), registerMessage.getPort());
                log.info("send game start to {}", url);
                MatchStartedMessage startEvent = new MatchStartedMessage();
                startEvent.setMatch_players(matchPlayers);
                startEvent.setDealer(dealerDto);
                startEvent.setYour_money(x.getStack());
                restTemplate.postForObject(url, startEvent, String.class);
            }
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

    public void sendInvalidRegistrationMessage(RegisterMessage registerMessage, String errorMsg) {
        //TODO: errorMsg is not send
        String url = String.format("http://%s:%d/start", registerMessage.getHostname(), registerMessage.getPort());
        log.info("send invalid registration message info to {}", url);
        GameEvent invalidRegMsg = new GameEvent();
        invalidRegMsg.setEventKind(GameEvent.EventKind.INVALID_REG_MSG);
        restTemplate.postForObject(url, invalidRegMsg, String.class);
    }
}
