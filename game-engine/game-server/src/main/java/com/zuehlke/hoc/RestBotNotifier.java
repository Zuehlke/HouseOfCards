package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.of;

@Component
public class RestBotNotifier implements BotNotifier {

    private static final Logger log = LoggerFactory.getLogger(RestBotNotifier.class.getName());

    private final Map<String, RegisterMessage> bots = new HashMap<>();
    private final Map<UUID, String> uuid2Bot = new HashMap<>();
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
        String url = String.format("http://%s:%d/register_info", registerMessage.getHostname(), registerMessage.getPort());
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setPlayerName(registerMessage.getPlayerName());
        UUID uuid = UUID.randomUUID();
        uuid2Bot.put(uuid, registerMessage.getPlayerName());
        registrationResponse.setUUID(uuid);
        if (bots.containsKey(registerMessage.getPlayerName())) {
            log.info("Name {} is already taken.", registerMessage.getPlayerName());
            registrationResponse.setInfoMessage(RegistrationResponse.Result.NAME_ALREADY_TAKEN);
            log.info("Send NAME_ALREADY_TAKEN message to {}.", url);
            restTemplate.postForObject(url, registrationResponse, String.class);
            return false;
        }
        log.info("Register bot: " + registerMessage.getPlayerName() + " -> " + registerMessage.getHostname() + ":" + registerMessage.getPort());
        bots.put(registerMessage.getPlayerName(), registerMessage);
        log.info("Current bots: " + bots.keySet().stream().reduce("", (a, b) -> a += (b + ", ")));
        registrationResponse.setInfoMessage(RegistrationResponse.Result.CONFIRMATION);
        log.info("Send RESERVATION_CONFIRMATION message for team {} to {}", registerMessage.getPlayerName(), url);
        restTemplate.postForObject(url, registrationResponse, String.class);
        return true;
    }

    @Override
    public Optional<String> getPlayer(UUID playerUUID) {
        return uuid2Bot.get(playerUUID) != null ? of(uuid2Bot.get(playerUUID)) : Optional.empty();
    }

    @Override
    public void sendMatchStartedMessage(List<PlayerInfo> players, PlayerInfo dealer) {
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
    public void sendRoundStarted(List<PlayerInfo> roundPlayers, int roundNumber, PlayerInfo dealer) {

        List<PlayerDTO> playerDTOs = roundPlayers.stream().map(x -> new PlayerDTO(x.getName(), x.getChipstack())).collect(Collectors.toList());

        PlayerDTO dealerDto = new PlayerDTO(dealer.getName(), dealer.getChipstack());

        roundPlayers.stream().forEach(x -> {
            RegisterMessage registerMessage = bots.get(x.getName());
            if (registerMessage == null) {
                log.info("Player {} cannot be associated with a URI", x.getName());
            } else {
                String url = String.format("http://%s:%d/round_started", registerMessage.getHostname(), registerMessage.getPort());
                log.info("send game start to {}", url);
                RoundStartedMessage roundStartedMessage = new RoundStartedMessage();
                roundStartedMessage.setRound_players(playerDTOs);
                roundStartedMessage.setRound_dealier(dealerDto);
                roundStartedMessage.setRound_number(roundNumber);
                restTemplate.postForObject(url, roundStartedMessage, String.class);
            }
        });
    }

    @Override
    public void sendYourTurn(Player player, long minimalBet, long maximalBet, long amountOfCreditsInPot, List<Player> activePlayers) {
        RegisterMessage uriAndPort = bots.get(player.getName());
        if (uriAndPort == null) {
            log.info("Player {} cannot be associated with a URI", player.getName());
        } else {
            String url = String.format("http://%s:%d/yourturn", uriAndPort.getHostname(), uriAndPort.getPort());
            //create YourTurn message
            YourTurnMessage yourTurnMessage = new YourTurnMessage();
            yourTurnMessage.setMinimum_set(minimalBet);
            yourTurnMessage.setMaximum_set(maximalBet);
            yourTurnMessage.setPot(amountOfCreditsInPot);

            //set the list of active players in message
            List<PlayerDTO> playerDTOs = activePlayers.stream().map(x -> new PlayerDTO(x.getName(), x.getChipsStack())).collect(Collectors.toList());
            yourTurnMessage.setActive_players(playerDTOs);

            //set the cards of the player in message
            List<Integer> cards = new ArrayList<>();
            if (player.getFirstCard() >= 0) {
                cards.add(player.getFirstCard());
            }
            if (player.getSecondCard() >= 0) {
                cards.add(player.getSecondCard());
            }
            yourTurnMessage.setYour_cards(cards);

            log.info("Request bet or fold from player: {}", player.getName());
            restTemplate.postForObject(url, yourTurnMessage, String.class);
        }
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
