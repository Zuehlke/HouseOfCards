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
        System.out.println("askPlayerForAction: " +name + ","+chipsToCall);
        notifier.playersTurn(name);
    }

    public void sendCardInfoToPlayer(String name, int card) {
            System.out.println("sendCardInfoToPlayer: " +name + ","+card);
            notifier.sendCardInfo(name, card);
    }

    public void broadcastMatchStart(Match match) {

        System.out.println("broadcastMatchStart");

        StartInfo startInfo = new StartInfo();

        List<String> players = new ArrayList<>();
        List<Player> matchPlayers = match.getMatchPlayers();

        matchPlayers.forEach(player -> players.add(player.getName()));
        matchPlayers.forEach(player -> startInfo.addPlayerInfo(new PlayerInfo(player.getName(), player.getChipsStack())));

        notifier.boradcastGameStarts(startInfo);
    }

    public void broadcastRoundStarts(){
        System.out.println("broadcastRoundStarts");
    }

    public void broadcastRoundFinished() {
        System.out.println("broadcastRoundFinished");
    }

    public void broadcastPlayerFolded(Player player) {
        System.out.println("broadcastPlayerFolded");
    }

    public void broadcastPlayerCalled(Player player) {
        System.out.println("broadcastPlayerCalled");
    }

    public void broadcastPlayerRaised(Player player, long raise) {
        System.out.println("broadcastPlayerRaised");
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
