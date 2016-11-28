package com.zuehlke.hoc.notification.api;

public interface PlayerNotifier {

    void sendCardInfo(String player, int card);
    void playersTurn(String player, long minimumChipsForCall);

    void broadcastGameStarts(StartInfo info);
    void broadcastPlayerRaised(String playerName, long amount);
    void broadcastPlayerCalled(String playerName);
    void broadcastPlayerFolded(String playerName);
    void broadcastNextRound();
    void broadcastNextMatch();
}
