package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.GameEvent;
import com.zuehlke.hoc.rest.PlayerDTO;
import com.zuehlke.hoc.rest.bot2server.RegisterMessage;
import com.zuehlke.hoc.rest.server2bot.*;
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
class RestBotNotifier implements BotNotifier {

    private static final Logger log = LoggerFactory.getLogger(RestBotNotifier.class.getName());
    private final Map<String, RegisterMessage> bots = new HashMap<>();
    private final Map<UUID, String> uuid2Bot = new HashMap<>();
    private final RestTemplate restTemplate;
    private final RegistrationService botRegistrationService;

    @Autowired
    public RestBotNotifier(RestTemplateBuilder restBuilder, RegistrationService botRegistrationService) {
        this.restTemplate = restBuilder.build();
        this.botRegistrationService = botRegistrationService;
    }

    @Override
    public boolean registerBot(RegisterMessage registerMessage) {
        String url = String.format("http://%s:%d/register_info", registerMessage.getHostname(), registerMessage.getPort());
        RegistrationInfoMessage registrationResponse = new RegistrationInfoMessage();
        registrationResponse.setPlayerName(registerMessage.getPlayerName());
        UUID uuid = UUID.randomUUID();
        uuid2Bot.put(uuid, registerMessage.getPlayerName());
        registrationResponse.setUUID(uuid);
        if (bots.containsKey(registerMessage.getPlayerName())) {
            log.info("Name {} is already taken.", registerMessage.getPlayerName());
            registrationResponse.setInfoMessage(RegistrationInfoMessage.Result.NAME_ALREADY_TAKEN);
            log.info("Send NAME_ALREADY_TAKEN message to {}.", url);
            restTemplate.postForObject(url, registrationResponse, String.class);
            return false;
        }
        log.info("Register bot: " + registerMessage.getPlayerName() + " -> " + registerMessage.getHostname() + ":" + registerMessage.getPort());
        bots.put(registerMessage.getPlayerName(), registerMessage);
        log.info("Current bots: " + bots.keySet().stream().reduce("", (a, b) -> a += (b + ", ")));
        registrationResponse.setInfoMessage(RegistrationInfoMessage.Result.CONFIRMATION);
        log.info("Send RESERVATION_CONFIRMATION message for team {} to {}", registerMessage.getPlayerName(), url);
        restTemplate.postForObject(url, registrationResponse, String.class);
        return true;
    }

    public void sendInvalidRegistrationMessage(RegisterMessage registerMessage, String errorMsg) {
        //TODO: errorMsg is not send
        String url = String.format("http://%s:%d/start", registerMessage.getHostname(), registerMessage.getPort());
        log.info("send invalid registration message info to {}", url);
        GameEvent invalidRegMsg = new GameEvent();
        invalidRegMsg.setEventKind(GameEvent.EventKind.INVALID_REG_MSG);
        restTemplate.postForObject(url, invalidRegMsg, String.class);
    }

    @Override
    public void sendRegistrationInfo(RegistrationInfoMessage registrationResponse) {
        Optional<String> urlOptional = this.botRegistrationService.getUriByPlayerName(registrationResponse.getPlayerName());
        urlOptional.ifPresent(url -> {
            log.info("Send ReservationInfo message for player {} to {}", registrationResponse.getPlayerName(), url);
            this.restTemplate.postForObject(url, registrationResponse, String.class);
        });
    }

    @Override
    public Optional<String> getPlayerNameByUuid(UUID playerUUID) {
        return uuid2Bot.get(playerUUID) != null ? of(uuid2Bot.get(playerUUID)) : Optional.empty();
    }


    @Override
    public void broadcastMatchStarted(List<Player> players, Player dealer) {
        players.forEach(p -> {
            if (bots.get(p.getName()) == null) {
                log.info("Player {} cannot be associated with a URI", p.getName());
            } else {
                MatchStartedMessage matchStartedMessage = buildMatchStartedMessage(players, dealer, p);
                String url = String.format("http://%s:%d/%s", bots.get(p.getName()).getHostname(), bots.get(p.getName()).getPort(), Endpoints.MATCH_STARTED.url);
                log.info("Send match_started to {}. Money: {}, Dealer: {}", url, matchStartedMessage.getYour_money(), matchStartedMessage.getDealer());
                restTemplate.postForObject(url, matchStartedMessage, String.class);
            }
        });
    }

    @Override
    public void broadcastRoundStarted(List<Player> roundPlayers, int roundNumber, Player dealer) {
        RoundStartedMessage roundStartedMessage = buildRoundStartedMessage(roundPlayers, roundNumber, dealer);
        broadcastMessage(roundStartedMessage, Endpoints.ROUND_STARTED.url);
    }

    @Override
    public void sendTurnRequest(Player player, long minimalBet, long maximalBet, long amountOfCreditsInPot, List<Player> activePlayers) {
        RegisterMessage uriAndPort = bots.get(player.getName());
        if (uriAndPort == null) {
            log.info("Player {} cannot be associated with a URI", player.getName());
        } else {
            String url = String.format("http://%s:%d/%s", uriAndPort.getHostname(), uriAndPort.getPort(), Endpoints.YOUR_TURN.url);
            TurnRequestMessage turnRequestMessage = buildYourTurnMessage(player, minimalBet, maximalBet, amountOfCreditsInPot, activePlayers);
            log.info("Request bet or fold from player: {}", player.getName());
            restTemplate.postForObject(url, turnRequestMessage, String.class);
        }
    }

    @Override
    public void broadcastMatchFinished(List<String> matchWinners) {
        MatchFinishedMessage matchFinishedMessage = new MatchFinishedMessage(matchWinners);
        broadcastMessage(matchFinishedMessage, Endpoints.MATCH_FINISHED.url);
        log.info("Broadcast match finished");
    }

    @Override
    public void broadcastGameFinished(String winnerName) {
        GameFinishedMessage gameFinishedMessage = new GameFinishedMessage(winnerName);
        broadcastMessage(gameFinishedMessage, Endpoints.GAME_FINISHED.url);
        log.info("Broadcast game finished");
    }

    @Override
    public void broadcastShowdown(List<Player> players) {
        ShowdownMessage showdownMessage = buildShowdownMessage(players);
        broadcastMessage(showdownMessage, Endpoints.SHOWDOWN.url);
    }

    @Override
    public void broadcastPlayerFolded(String playerName) {
        FoldMessage foldMessage = new FoldMessage(playerName);
        broadcastMessage(foldMessage, Endpoints.PLAYER_FOLDED.url);
        log.info("Broadcast player {} folded", playerName);
    }

    @Override
    public void broadcastPlayerSet(String playerName, long amount) {
        SetMessage setMessage = new SetMessage(playerName, amount);
        broadcastMessage(setMessage, Endpoints.PLAYER_SET.url);
        log.info("Broadcast layer {} set {}", playerName, amount);
    }


    private MatchStartedMessage buildMatchStartedMessage(List<Player> players, Player dealer, Player p) {
        MatchStartedMessage matchStartedMessage = new MatchStartedMessage();
        List<PlayerDTO> matchPlayers = players.stream().map(x -> new PlayerDTO(x.getName(), x.getChipsStack())).collect(Collectors.toList());
        matchStartedMessage.setMatch_players(matchPlayers);
        PlayerDTO dealerDto = new PlayerDTO(dealer.getName(), dealer.getChipsStack());
        matchStartedMessage.setDealer(dealerDto);
        matchStartedMessage.setYour_money(p.getChipsStack());
        return matchStartedMessage;
    }

    private RoundStartedMessage buildRoundStartedMessage(List<Player> roundPlayers, int roundNumber, Player dealer) {
        RoundStartedMessage roundStartedMessage = new RoundStartedMessage();
        List<PlayerDTO> playerDTOs = roundPlayers.stream().map(x -> new PlayerDTO(x.getName(), x.getChipsStack())).collect(Collectors.toList());
        roundStartedMessage.setRound_players(playerDTOs);
        PlayerDTO dealerDto = new PlayerDTO(dealer.getName(), dealer.getChipsStack());
        roundStartedMessage.setRound_dealier(dealerDto);
        roundStartedMessage.setRound_number(roundNumber);
        return roundStartedMessage;
    }

    private TurnRequestMessage buildYourTurnMessage(Player player, long minimalBet, long maximalBet, long amountOfCreditsInPot, List<Player> activePlayers) {
        TurnRequestMessage turnRequestMessage = new TurnRequestMessage();
        turnRequestMessage.setMinimum_set(minimalBet);
        turnRequestMessage.setMaximum_set(maximalBet);
        turnRequestMessage.setPot(amountOfCreditsInPot);

        //set the list of active players in message
        List<PlayerDTO> playerDTOs = activePlayers.stream().map(x -> new PlayerDTO(x.getName(), x.getChipsStack())).collect(Collectors.toList());
        turnRequestMessage.setActive_players(playerDTOs);

        //set the cards of the player in message
        List<Integer> cards = new ArrayList<>();
        if (player.getFirstCard() >= 0) {
            cards.add(player.getFirstCard());
        }
        if (player.getSecondCard() >= 0) {
            cards.add(player.getSecondCard());
        }
        turnRequestMessage.setYour_cards(cards);
        return turnRequestMessage;
    }

    private ShowdownMessage buildShowdownMessage(List<Player> players) {
        ShowdownMessage showdownMessage = new ShowdownMessage();
        Map<String, List<Integer>> showDownPlayers = new HashMap<>();
        players.forEach(player -> {
            List<Integer> hand = new ArrayList<>();
            hand.add(player.getFirstCard());
            hand.add(player.getSecondCard());
            showDownPlayers.put(player.getName(), hand);
        });
        showdownMessage.setPlayers(showDownPlayers);
        return showdownMessage;
    }


    /**
     * Broadcasts a message to all registered bots.
     *
     * @param message the message containing the information to be transmitted
     */
    private void broadcastMessage(Message message, String endpoint) {
        bots.values().forEach(bot -> {
            String url = String.format("http://%s:%d/%s", bot.getHostname(), bot.getPort(), endpoint);
            restTemplate.postForObject(url, message, String.class);
        });
    }


    private enum Endpoints {
        PLAYER_FOLDED("player_folded"),
        PLAYER_SET("player_set"),
        MATCH_STARTED("match_started"),
        ROUND_STARTED("round_started"),
        YOUR_TURN("your_turn"),
        SHOWDOWN("showdown"),
        MATCH_FINISHED("match_finished"),
        GAME_FINISHED("game_finished");

        private final String url;

        Endpoints(String url) {
            this.url = url;
        }
    }
}
