package com.zuehlke.hoc.actors;

import com.zuehlke.hoc.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public interface BotNotifier {

    void gameStartEvent();

    void sendPlayerInfo(Player player);

}
