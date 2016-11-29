package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.rest.RegisterMessage;

public interface BotNotifier {

    boolean registerBot(RegisterMessage registerMessage);

    void gameStartEvent();

    void sendPlayerInfo(Player player);

}
