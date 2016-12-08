package com.zuehlke.hoc;

import com.zuehlke.hoc.actors.BotNotifier;
import com.zuehlke.hoc.actors.EngineActor;
import com.zuehlke.hoc.actors.ViewNotifier;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.PlayerNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void playersTurn(String playerName, long minimumChipsForCall, NokerGame game) {
        //viewNotifier.sendGameInfo("Next turn: Player "+player);
        log.info("playersTurn: player: {}", playerName);
        Optional<Player> player = game.getPlayer(playerName);
        List<Integer> cards = new ArrayList<>();
        player.map(x -> {
            if (x.getFirstCard() >= 0) {
                        cards.add(x.getFirstCard());
                    }
            if (x.getSecondCard() >= 0) {
                        cards.add(x.getSecondCard());
                    }
                    //TODO: the arguments maximalBet, amountOfCreditsInPot and activePlayers are dummies argument and need to
            // be replaced with meaningful value as soon the NokerGame API provides the correspondings methods.
                    botNotifier.sendYourTurn(x.getName(), minimumChipsForCall, Integer.MAX_VALUE, 100, cards, new ArrayList<PlayerInfo>());
                    return x;
                }
        );
    }

    @Override
    public void matchStarted(List<Player> players, Player dealer) {
        log.info("A new match started");
    }

    @Override
    public void broadcastNextRound() {
        viewNotifier.sendGameInfo("New round started");

    }

    @Override
    public void broadcastNextMatch() {
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
