package com.zuehlke.houseofcards;

import com.zuehlke.houseofcards.events.DealCardEvent;
import com.zuehlke.houseofcards.model.Match;
import com.zuehlke.houseofcards.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerNotifierAdapter {

    private PlayerNotifier notifier;

    public PlayerNotifierAdapter(PlayerNotifier notifier) {
        this.notifier = notifier;
    }

    public void publishStart(Match match) {
        StartInfo startInfo = new StartInfo();

        List<String> players = new ArrayList<>();
        List<Player> matchPlayers = match.getMatchPlayers();

        matchPlayers.forEach(player -> players.add(player.getName()));
        matchPlayers.forEach(player -> startInfo.addPlayerInfo(new PlayerInfo(player.getName(), player.getChipsStack())));

        GameEvent gameEvent = new GameEvent(players, startInfo.toString());

        publishGameEvent(gameEvent);
    }

    public void publishGameEvent(GameEvent gameEvent) {
        notifier.broadcast(gameEvent);
    }

    public void askPlayerForAction(Player player) {
        List<String> recievers = new ArrayList<>();
        recievers.add(player.getName());
        GameEvent gameEvent = new GameEvent(recievers, "Your turn!");

        notifier.publishToPlayer(gameEvent);
    }

    public void publishToPlayer(DealCardEvent dealCardEvent) {
        List<String> recievers = new ArrayList<>();
        recievers.add(dealCardEvent.getPlayer().getName());
        GameEvent gameEvent = new GameEvent(recievers, ""+dealCardEvent.getCard());

        notifier.publishToPlayer(gameEvent);
    }
}
