package com.zuehlke.hoc;

import java.util.List;

public interface Game {

    public void addPlayer(Player p);
    public List<Player> getPlayers();
    public boolean isReady();
    public void start();
    public void handleAction(Action action);


}
