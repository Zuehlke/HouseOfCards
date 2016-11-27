package com.zuehlke.houseofcards;

import com.zuehlke.houseofcards.model.Match;
import com.zuehlke.houseofcards.model.Player;
import com.zuehlke.houseofcards.notification.api.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerNotifierAdapter {

    private PlayerNotifier notifier;

    public PlayerNotifierAdapter(PlayerNotifier notifier) {
        this.notifier = notifier;
    }


    public void publishFinish(Match match) {
        StartInfo startInfo = new StartInfo();

        List<String> players = new ArrayList<>();
        List<Player> matchPlayers = match.getMatchPlayers();

        matchPlayers.forEach(player -> players.add(player.getName()));
        matchPlayers.forEach(player -> startInfo.addPlayerInfo(new PlayerInfo(player.getName(), player.getChipsStack())));

        notifier.boradcastGameStarts(startInfo);
    }


    public void askPlayerForAction(String name, long chipsToCall) {
        notifier.playersTurn(name);
    }

    public void sendCardInfoToPlayer(String name, int card) {
            notifier.sendCardInfo(name, card);
    }

    public void broadcastMatchStart(Match match) {
        StartInfo startInfo = new StartInfo();

        List<String> players = new ArrayList<>();
        List<Player> matchPlayers = match.getMatchPlayers();

        matchPlayers.forEach(player -> players.add(player.getName()));
        matchPlayers.forEach(player -> startInfo.addPlayerInfo(new PlayerInfo(player.getName(), player.getChipsStack())));

        notifier.boradcastGameStarts(startInfo);
    }

    public void broadcastRoundStarts(){}

    public void broadcastRoundFinished() {}

    public void broadcastPlayerFolded(Player player) {
    }

    public void broadcastPlayerCalled(Player player) {
    }

    public void broadcastPlayerRaised(Player player, long raise) {
    }

    public void sendGameFinisedToPlayer(Player p) {
    }

    public void broadcastMatchFinished() {
    }

    public void broadcastGameFinished() {
    }

    public void broadcastGameStarted() {
    }
}
