package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.actors.ViewNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.NokerGameObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handles callbacks from <code>NokerGame</code> instance and forwards notifications to the web view and the registered
 * bots.
 *
 * @author Lukas Hofmaier
 */
public class WebViewAndBotNotifier implements NokerGameObserver {

    private final static Logger log = LoggerFactory.getLogger(WebViewAndBotNotifier.class);
    private final BotNotifier botNotifier;
    private final ViewNotifier viewNotifier;



    public WebViewAndBotNotifier(BotNotifier botNotifier, ViewNotifier viewNotifier) {
        this.botNotifier = botNotifier;
        this.viewNotifier = viewNotifier;
    }

    @Override
    public void requestTurn(Player player, long lowerBound, long upperBound, long amountInPot, List<Player> activePlayers) {
        log.info("requestTurn: player: {}", player.getName());
        botNotifier.sendTurnRequest(player, lowerBound, upperBound, amountInPot, activePlayers);
    }

    @Override
    public void matchStarted(List<Player> players, Player dealer) {
        botNotifier.broadcastMatchStarted(players, dealer);
        log.info("A new match started");
    }

    @Override
    public void roundStarted(List<Player> players, Player dealer, int roundNumber) {
        botNotifier.broadcastRoundStarted(players, roundNumber, dealer);
        log.info("A new round started");
    }

    @Override
    public void playerFolded(String playerName) {
        botNotifier.broadcastPlayerFolded(playerName);
        log.info("Player folded: {}", playerName);
    }

    @Override
    public void playerSet(String playerName, long amount) {
        botNotifier.broadcastPlayerSet(playerName, amount);
        log.info("Player set: {}, {}", playerName, amount);
    }

    @Override
    public void matchFinished(List<String> matchWinners) {
        botNotifier.broadcastMatchFinished(matchWinners);
        log.info("Match finished");
    }

    @Override
    public void showdown(List<Player> players) {
        botNotifier.broadcastShowdown(players);
        log.info("Showdown");
    }

    @Override
    public void gameFinished(String winnerName) {
        botNotifier.broadcastGameFinished(winnerName);
        log.info("Game finished");
    }
}
