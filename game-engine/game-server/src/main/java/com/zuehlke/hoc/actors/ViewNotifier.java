package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.model.Player;

public interface ViewNotifier {
    void sendGameInfo();

    void onRegisterPlayer(Player p);
}
