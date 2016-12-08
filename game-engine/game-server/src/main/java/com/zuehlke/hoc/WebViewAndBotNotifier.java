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
    public void sendCardInfo(String player, int card) {
        log.info("Send card info");
        viewNotifier.sendGameInfo("Player " + player + " got card " + card);
    }

    @Override
    public void requestBet(Player player, long lowerBound, long upperBound, long amountInPot, List<Player> activePlayers) {
        //viewNotifier.sendGameInfo("Next turn: Player "+player);
        log.info("requestBet: player: {}", player.getName());


        botNotifier.sendYourTurn(player, lowerBound, upperBound, amountInPot, activePlayers);
    }
        )
}

    @Override
    public void matchStarted(List<Player> players, Player dealer) {
        log.info("Broadcast match started to all registered players");
        viewNotifier.sendGameInfo("A new game started");
        // botNotifier.sendMatchStartedMessage(info.getPlayerInfos(), info.getPlayerInfos().get(0));
        // botNotifier.sendRoundStarted(info.getPlayerInfos(), 0, info.getPlayerInfos().get(0));
    }

    @Override
    public void broadcastPlayerRaised(String playerName, long amount) {
        viewNotifier.sendGameInfo("Player " + playerName + " raised");
        log.info("{} raised {}", playerName, amount);
    }

    @Override
    public void broadcastPlayerCalled(String playerName) {
        viewNotifier.sendGameInfo("Player " + playerName + " called");


    }

    @Override
    public void broadcastPlayerFolded(String playerName) {
        viewNotifier.sendGameInfo("Player " + playerName + " folded");

    }

    @Override
    public void broadcastNextRound() {
        viewNotifier.sendGameInfo("New round started");

    }

    @Override
    public void broadcastNextMatch() {
        viewNotifier.sendGameInfo("New match started");

    }

}
