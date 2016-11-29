package com.zuehlke.hoc;


import com.zuehlke.hoc.Exceptions.InitGameException;
import com.zuehlke.hoc.model.NokerDeck;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.PlayerNotifier;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class NokerGameTest {

    // This method is a demonstration of the interaction between players
    // and the game domain.
    @Test
    public void demo(){

        PlayerNotifier playerNotifier = Mockito.mock(PlayerNotifier.class);

        NokerGame game = new NokerGame(3, playerNotifier);

        Player tobi = game.createPlayer("tobi");
        Player riki = game.createPlayer("riki");
        Player trump = game.createPlayer("trump");


        // first match
        // initial players state
        // Players: [{name='tobi', chipsStack=100},
        //          {name='riki', chipsStack=100},
        //          {name='trump', chipsStack=100}]

        // first round
        game.playerCall(tobi);
        game.playerCall(riki);
        game.playerRaise(trump, 10);
        game.playerCall(tobi);
        game.playerFold(riki);

        //second round
        game.playerRaise(tobi,20);
        game.playerRaise(trump, 20);
        game.playerCall(tobi);


        // second match
        // example state after first match:
        // Players: [{name='tobi', chipsStack=50},
        //          {name='riki', chipsStack=100},
        //          {name='trump', chipsStack=150}]

        // first round
        game.playerRaise(riki, 10);
        game.playerFold(trump);
        game.playerCall(tobi);

        // second round
        game.playerRaise(riki, 20);
        game.playerCall(tobi);

        // ...
    }


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
        PlayerNotifier playerNotifier = Mockito.mock(PlayerNotifier.class);

        NokerGame game = new NokerGame(3, playerNotifier);

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
        PlayerNotifier playerNotifier = Mockito.mock(PlayerNotifier.class);

        NokerGame game = new NokerGame(3, playerNotifier);

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