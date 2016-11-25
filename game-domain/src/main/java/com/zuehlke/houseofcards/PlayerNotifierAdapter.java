package com.zuehlke.houseofcards;

import com.sun.tools.javadoc.Start;

public class PlayerNotifierAdapter {

    private PlayerNotifier notifier;

    public PlayerNotifierAdapter(PlayerNotifier notifier) {
        this.notifier = notifier;
    }

    public void publishStart(State state) {

        //TODO: mapping from domain objects to the notifier input
        StartInfo info = null;
        notifier.publishStart(info);
        publishGameEvent();
    }

    public void publishGameEvent() {
        notifier.publishGameEvent(null);
    }

    public void askPlayerFoAction() {
        notifier.askPlayerFoAction(null);
    }
}
