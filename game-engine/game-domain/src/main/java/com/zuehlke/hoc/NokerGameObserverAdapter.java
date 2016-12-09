package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.NokerGameObserver;
import com.zuehlke.hoc.notification.api.StartInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class NokerGameObserverAdapter {

    private static final Logger log = LoggerFactory.getLogger(NokerGameObserverAdapter.class.getName());
    private NokerGameObserver notifier;


    public NokerGameObserverAdapter(NokerGameObserver notifier) {
        this.notifier = notifier;
    }

    public void askPlayerForAction(String name, long chipsToCall) {
        log.info("Ask player for action: Player in turn: {}, Chips to call: {}", name, chipsToCall);
    }

    public void sendCardInfoToPlayer(String name, int card) {
        log.info("Send card to player: Player: {}, Card: {}", name, card);
    }

    public void broadcastMatchStart(Match match) {
        StartInfo startInfo = new StartInfo();

        List<String> players = new ArrayList<>();
        List<Player> matchPlayers = match.getMatchPlayers();

        matchPlayers.forEach(player -> players.add(player.getName()));
        matchPlayers.forEach(player -> startInfo.addPlayerInfo(new PlayerInfo(player.getName(), player.getChipsStack())));

        log.info("Match started: Players: {}", matchPlayers);
    }

    public void broadcastPlayerFolded(Player player) {
        notifier.playerFolded(player.getName());
        log.info("Player folded: Player: {}", player.getName());
    }

    public void broadcastPlayerSet(Player player, long amount) {
        notifier.playerSet(player.getName(), amount);
        log.info("Player set: Player: {}, {}", player.getName(), amount);
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
