package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class PlayerNotifierAdapter {

    private PlayerNotifier notifier;

    private static final Logger log = LoggerFactory.getLogger(PlayerNotifierAdapter.class.getName());


    public PlayerNotifierAdapter(PlayerNotifier notifier) {
        this.notifier = notifier;
    }

    public void askPlayerForAction(String name, long chipsToCall) {
        log.info("Ask player for action: Player in turn: {}, Chips to call: {}", name, chipsToCall);
        notifier.playersTurn(name, chipsToCall);
    }

    public void sendCardInfoToPlayer(String name, int card) {
        log.info("Send card to player: Player: {}, Card: {}", name, card);
        notifier.sendCardInfo(name, card);
    }

    public void broadcastMatchStart(Match match) {
        StartInfo startInfo = new StartInfo();

        List<String> players = new ArrayList<>();
        List<Player> matchPlayers = match.getMatchPlayers();

        matchPlayers.forEach(player -> players.add(player.getName()));
        matchPlayers.forEach(player -> startInfo.addPlayerInfo(new PlayerInfo(player.getName(), player.getChipsStack())));

        log.info("Match started: Players: {}", matchPlayers);
        notifier.broadcastGameStarts(startInfo);
    }

    public void broadcastPlayerFolded(Player player) {
        log.info("Player folded: Player: {}", player.getName());
        notifier.broadcastPlayerFolded(player.getName());
    }

    public void broadcastPlayerCalled(Player player) {
        log.info("Player called: Player: {}", player.getName());
        notifier.broadcastPlayerCalled(player.getName());
    }

    public void broadcastPlayerRaised(Player player, long raise) {
        log.info("Player raised: Player: {}, Raise: {}", player.getName(), raise);
        notifier.broadcastPlayerRaised(player.getName(), raise);
    }

    public void broadcastRoundStarts(){
        log.info("Round started");
    }

    public void broadcastRoundFinished() {
        log.info("Round finished");
    }

    public void broadcastMatchFinished(List<Player> winners) {
        log.info("Match finished: Winner(s): {}", winners);
    }

    public void broadcastGameFinished() {
        log.info("Game finished");
    }

    public void broadcastGameStarted() {
        log.info("Game started");
    }

}
