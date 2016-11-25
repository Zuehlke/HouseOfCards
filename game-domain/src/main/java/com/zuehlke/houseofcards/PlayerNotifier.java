package com.zuehlke.houseofcards;

public interface PlayerNotifier {

    void publishStart(StartInfo startInfo);
    void publishToPlayer(GameEvent event);
    void broadcastGameEvent(GameEvent event);
    void askPlayerForAction(PlayerAction action);

}
