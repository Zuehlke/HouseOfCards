package com.zuehlke.hoc.notification.api;

import java.io.Serializable;

public interface PlayerNotifier {


    void playersTurn(String player);
    void sendCardInfo(String player, int card);

    void boradcastGameStarts(StartInfo info);
    void boradcastNextRound();
    void boradcastNextMatch();
    void broadcastPlayerRaised(String name, long amount);
    void broadcastPlayerCalled(String name);
    void boradcastPlayerFolded(String name);


}
