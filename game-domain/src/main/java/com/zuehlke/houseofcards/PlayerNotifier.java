package com.zuehlke.houseofcards;

public interface PlayerNotifier {

    void publishToPlayer(GameEvent event);
    void broadcast(GameEvent event);
}
