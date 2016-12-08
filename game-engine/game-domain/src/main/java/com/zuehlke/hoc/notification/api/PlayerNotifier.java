package com.zuehlke.hoc.notification.api;

import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.model.Player;

import java.util.List;

public interface PlayerNotifier {

    void sendCardInfo(String player, int card);

    void playersTurn(String player, long minimumChipsForCall, NokerGame nokerGame);

    void matchStarted(List<Player> player, Player dealer);
    void broadcastPlayerRaised(String playerName, long amount);
    void broadcastPlayerCalled(String playerName);
    void broadcastPlayerFolded(String playerName);
    void broadcastNextRound();
    void broadcastNextMatch();
}
