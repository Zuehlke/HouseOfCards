package com.zuehlke.houseofcards;

public interface PlayerNotifier {

    void publishStart(StartInfo startInfo);

    void publishGameEvent(GameEvent event);

    void askPlayerFoAction(PlayerAction action);

}
