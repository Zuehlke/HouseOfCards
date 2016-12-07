package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.actors.EngineActor;
import com.zuehlke.hoc.actors.ViewNotifier;
import com.zuehlke.hoc.notification.api.PlayerNotifier;
import com.zuehlke.hoc.notification.api.StartInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles callbacks from <code>NonkerGame</code> instance and forwards notifications to the web view and the registered
 * bots.
 *
 * @author Lukas Hofmaier
 */
public class WebViewAndBotNotifier implements PlayerNotifier {

    private final static Logger log = LoggerFactory.getLogger(EngineActor.class);
    private final BotNotifier botNotifier;
    private final ViewNotifier viewNotifier;

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
    public void playersTurn(String player, long minimumChipsForCall) {
        //viewNotifier.sendGameInfo("Next turn: Player "+player);
        log.info("playersturn");
    }

    @Override
    public void broadcastGameStarts(StartInfo info) {
        log.info("Broadcast game start event to all registered players");
        viewNotifier.sendGameInfo("A new game started: " + info.toString());
        botNotifier.gameStartEvent(info.getPlayerInfos(), info.getPlayerInfos().get(0));
        botNotifier.sendRoundStarted(info.getPlayerInfos(), 0, info.getPlayerInfos().get(0));
    }

    @Override
    public void broadcastPlayerRaised(String playerName, long amount) {
        viewNotifier.sendGameInfo("Player " + playerName + " raised");

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
