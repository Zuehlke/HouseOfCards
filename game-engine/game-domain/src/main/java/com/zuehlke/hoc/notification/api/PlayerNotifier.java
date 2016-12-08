package com.zuehlke.hoc.notification.api;


import java.util.List;

import com.zuehlke.hoc.NokerGame;
import com.zuehlke.hoc.model.Player;


public interface PlayerNotifier {

    void sendCardInfo(String player, int card);

    void broadcastGameStarted(StartInfo info);
    void broadcastMatchStarted(List<Player> player, Player dealer);
    void broadcastNextMatch();
    void broadcastNextRound();

    void playersTurn(String player, long minimumChipsForCall, NokerGame nokerGame);
    void broadcastMatchFinished(List<Player> matchWinners, long pot);
    void broadcastShowdown(List<Player> players);
    void broadcastGameFinished(Player player);

    void broadcastPlayerFolded(Player player);
    void broadcastPlayerSet(Player player, long amount);
}
