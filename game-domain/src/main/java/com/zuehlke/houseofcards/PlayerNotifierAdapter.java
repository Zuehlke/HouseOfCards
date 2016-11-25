package com.zuehlke.houseofcards;

import com.zuehlke.houseofcards.events.DealCardEvent;
import com.zuehlke.houseofcards.model.Match;

import java.util.ArrayList;
import java.util.List;

public class PlayerNotifierAdapter {

    private PlayerNotifier notifier;

    public PlayerNotifierAdapter(PlayerNotifier notifier) {
        this.notifier = notifier;
    }

    public void publishStart(Match match) {
        StartInfo startInfo = new StartInfo();

        List<Player> players = match.getMatchPlayers();
        players.forEach(player -> startInfo.addPlayerInfo(new PlayerInfo(player.getName(), player.getChipsStack())));

        notifier.publishStart(startInfo);
    }

    public void publishGameEvent() {
        notifier.broadcastGameEvent(null);
    }

    public void askPlayerForAction() {
        notifier.askPlayerForAction(null);
    }

    public void publishToPlayer(DealCardEvent dealCardEvent) {
        List<String> recievers = new ArrayList<>();
        recievers.add(dealCardEvent.getPlayer().getName());
        GameEvent gameEvent = new GameEvent(recievers, ""+dealCardEvent.getCard());

        notifier.publishToPlayer(gameEvent);
    }
}
