package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerNotifierAdapter {

    private PlayerNotifier notifier;

    public PlayerNotifierAdapter(PlayerNotifier notifier) {
        this.notifier = notifier;
    }

    public void askPlayerForAction(String name, long chipsToCall) {
        notifier.playersTurn(name, chipsToCall);
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

        notifier.broadcastGameStarts(startInfo);
    }

    public void broadcastPlayerFolded(Player player) {
        notifier.broadcastPlayerFolded(player.getName());
    }

    public void broadcastPlayerCalled(Player player) {
        notifier.broadcastPlayerCalled(player.getName());
    }

    public void broadcastPlayerRaised(Player player, long raise) {
        notifier.broadcastPlayerRaised(player.getName(), raise);
    }

    public void broadcastRoundStarts(){
        System.out.println("broadcastRoundStarts");
    }

    public void broadcastRoundFinished() {
        System.out.println("broadcastRoundFinished");
    }

    public void broadcastMatchFinished() {
        System.out.println("broadcastMatchFinished");
    }

    public void broadcastGameFinished() {
        System.out.println("broadcastGameFinished");
    }

    public void broadcastGameStarted() {
        System.out.println("broadcastGameStarted");
    }
}
