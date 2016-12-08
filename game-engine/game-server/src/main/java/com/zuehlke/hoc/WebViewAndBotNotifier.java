package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.actors.EngineActor;
import com.zuehlke.hoc.actors.ViewNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.PlayerNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Handles callbacks from <code>NokerGame</code> instance and forwards notifications to the web view and the registered
 * bots.
 *
 * @author Lukas Hofmaier
 */
public class WebViewAndBotNotifier implements PlayerNotifier {

    private final static Logger log = LoggerFactory.getLogger(EngineActor.class);
    private final BotNotifier botNotifier;
    private final ViewNotifier viewNotifier;

    //private NokerGame game;

    public WebViewAndBotNotifier(BotNotifier botNotifier, ViewNotifier viewNotifier) {
        this.botNotifier = botNotifier;
        this.viewNotifier = viewNotifier;
    }

    @Override
    public void requestBet(Player player, long lowerBound, long upperBound, long amountInPot, List<Player> activePlayers) {
        //viewNotifier.sendGameInfo("Next turn: Player "+player);
        log.info("requestBet: player: {}", player.getName());
        botNotifier.sendYourTurn(player, lowerBound, upperBound, amountInPot, activePlayers);
    }

    @Override
    public void matchStarted(List<Player> players, Player dealer) {
        log.info("A new match started");
    }

    @Override
    public void roundStarted(List<Player> players, Player dealer, int roundNumber) {
        viewNotifier.sendGameInfo("New match started");

    }

    @Override
    public void broadcastMatchFinished(List<Player> matchWinners, long pot) {

    }

    @Override
    public void broadcastShowdown(List<Player> players) {

    }

    @Override
    public void broadcastGameFinished(Player player) {

    }

    @Override
    public void broadcastPlayerFolded(Player player) {
        botNotifier.playerFolded(player.getName());
        log.info("Player folded: {}", player.getName());
    }

    @Override
    public void broadcastPlayerSet(Player player, long amount) {

    }
}
