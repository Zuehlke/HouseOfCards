package com.zuehlke.hoc;


import com.zuehlke.hoc.Exceptions.InitGameException;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.NokerGameObserver;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;


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
        NokerGame game = new NokerGame(NokerSettings.MAX_NUM_OF_PLAYERS + 1, null);
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
        Player john = game.createPlayer("john");

        players.add(tobi);
        players.add(riki);
        players.add(john);

        players.forEach(player -> {
            assertNotNull(player.getFirstCard());
            assertEquals(NokerSettings.INITIAL_CHIPS - NokerSettings.BLIND, player.getChipsStack());
        });
    }

    @Test
    public void getExistingPlayersByName() {
        NokerGameObserver playerNotifier = Mockito.mock(NokerGameObserver.class);

        NokerGame game = new NokerGame(3, playerNotifier);

        Player tobi = game.createPlayer("tobi");
        Player riki = game.createPlayer("riki");
        Player trump = game.createPlayer("john");

        Optional<Player> gamePlayer = game.getPlayer("tobi");

        assertTrue(gamePlayer.isPresent());
        assertEquals(tobi, gamePlayer.get());
    }

    @Test(expected = NoSuchElementException.class)
    public void getNonExistingPlayersByName() {
        NokerGameObserver playerNotifier = Mockito.mock(NokerGameObserver.class);

        NokerGame game = new NokerGame(3, playerNotifier);

        game.createPlayer("tobi");
        game.createPlayer("riki");
        game.createPlayer("john");

        Optional<Player> gamePlayer = game.getPlayer("jerry");

        assertFalse(gamePlayer.isPresent());
        gamePlayer.get();
    }

    @Test
    public void gameFinished() {
        NokerGameObserver playerNotifier = Mockito.mock(NokerGameObserver.class);

        NokerGame game = new NokerGame(2, playerNotifier);

        Player tobi = game.createPlayer("tobi");
        Player riki = game.createPlayer("riki");

        game.playerSet(tobi, 95);
        game.playerSet(riki, 95);

        game.playerFold(tobi);

        System.out.println();
    }
}