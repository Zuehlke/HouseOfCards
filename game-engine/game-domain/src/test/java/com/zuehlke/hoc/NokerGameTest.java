package com.zuehlke.hoc;


import com.zuehlke.hoc.Exceptions.InitGameException;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.NokerGameObserver;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class NokerGameTest {


    @Test(expected = InitGameException.class)
    public void initGameWithTooLittlePlayers() {
        NokerGame game = new NokerGame(1, null);
    }


    @Test(expected = InitGameException.class)
    public void initGameWithZeroPlayers() {
        NokerGame game = new NokerGame(0, null);
    }


    @Test(expected = InitGameException.class)
    public void initGameWithTooManyPlayers() {
        NokerGame game = new NokerGame(NokerGame.MAX_NUM_OF_PLAYERS + 1, null);
    }


    @Test
    public void notEnoughPlayersToStartGame() {
        List<Player> players = new ArrayList<>();
        NokerGameObserver nokerGameObserver = Mockito.mock(NokerGameObserver.class);

        NokerGame game = new NokerGame(3, nokerGameObserver);

        Player tobi = game.createPlayer("tobi");
        Player riki = game.createPlayer("riki");

        players.add(tobi);
        players.add(riki);

        players.forEach(player -> {
            assertEquals(0, player.getChipsStack());
        });
    }


    @Test
    public void addPlayersStartsGame() {
        List<Player> players = new ArrayList<>();
        NokerGameObserver nokerGameObserver = Mockito.mock(NokerGameObserver.class);

        NokerGame game = new NokerGame(3, nokerGameObserver);

        Player tobi = game.createPlayer("tobi");
        Player riki = game.createPlayer("riki");
        Player trump = game.createPlayer("trump");

        players.add(tobi);
        players.add(riki);
        players.add(trump);

        players.forEach(player -> {
            assertNotNull(player.getFirstCard());
            assertEquals(NokerGame.INITIAL_CHIPS, player.getChipsStack());
        });
    }
}