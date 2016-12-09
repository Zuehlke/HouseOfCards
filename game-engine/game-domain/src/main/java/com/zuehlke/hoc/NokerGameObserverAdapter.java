package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.NokerGameObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * The {@link NokerGameObserverAdapter} class is used by the NokerGame to notify it's
 * environment about changes in the game state. The adapter maps its inputs and calls
 * delegates the notifications to the {@link NokerGameObserver}.
 */
public class NokerGameObserverAdapter {

    private static final Logger log = LoggerFactory.getLogger(NokerGameObserverAdapter.class.getName());
    private NokerGameObserver notifier;


    public NokerGameObserverAdapter(NokerGameObserver notifier) {
        this.notifier = notifier;
    }


    public void askPlayerForAction(String name, long minimumToSet, long maximumToSet) {
        log.info("Ask player for action: Player in turn: {}, Minimum to set: {}, Maximum to set: {}", name, minimumToSet, maximumToSet);
    }

    public void sendCardInfoToPlayer(String name, int card) {
        log.info("Send card to player: Player: {}, Card: {}", name, card);
    }

    public void broadcastMatchStart(Match match) {
        log.info("Match started, Players: {}", match.getMatchPlayers());
    }

    public void broadcastPlayerFolded(Player player) {
        notifier.playerFolded(player.getName());
        log.info("Player folded: Player: {}", player.getName());
    }

    public void broadcastPlayerSet(Player player, long amount) {
        notifier.playerSet(player.getName(), amount);
        log.info("Player set: Player: {}, {}", player.getName(), amount);
    }

    // TODO: adapt the parameters according to the protocol
    public void broadcastRoundStarts(){
        log.info("Round started");
    }


    public void broadcastMatchFinished(List<Player> winners) {
        List<String> winnersNames = new ArrayList<>();
        winners.forEach(winner -> winnersNames.add(winner.getName()));
        notifier.matchFinished(winnersNames);
        log.info("Match finished: Winner(s): {}", winners);
    }


    // TODO: adapt the parameters according to the protocol => add the game winner's name
    public void broadcastGameFinished() {
        log.info("Game finished");
    }


    // This notification is not defined in the game protocol and must therefore
    // not be delegated to the NokerGameObserver. It can be used for logging the
    // event.
    public void broadcastRoundFinished() {
        log.info("Round finished");
    }

    // This notification is not defined in the game protocol and must therefore
    // not be delegated to the NokerGameObserver. It can be used for logging the
    // event.
    public void broadcastGameStarted(List<Player> gamePlayers) {
        log.info("Game started");
    }

    public void broadcastPlayerJoined(String playerName) {
        log.info("Player joined: Player: {}", playerName);
    }
}
