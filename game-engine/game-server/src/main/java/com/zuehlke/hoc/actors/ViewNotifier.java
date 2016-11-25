package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.Player;

public interface ViewNotifier {
    void sendGameInfo();

    void onRegisterPlayer(Player p);
}
