package com.zuehlke.houseofcards;

import com.zuehlke.houseofcards.Exceptions.ExceededMaxPlayersException;
import com.zuehlke.houseofcards.NokerGame;
import com.zuehlke.houseofcards.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class NokerGameTest {

    private NokerGame game;

    @Before
    public void setup() {
        game = new NokerGame();
    }

    @Test
    public void addPlayers() {
        game.addPlayer(new Player("John"));
        game.addPlayer(new Player("Pete"));
        Assert.assertEquals(game.getAllPlayers().size(), 2);
    }

    @Test
    public void notEnoughPlayersToStartGame() {
        game.addPlayer(new Player("John"));
        assertFalse(game.isReady());
    }

    @Test
    public void enoughPlayersToStartGame() {
        game.addPlayer(new Player("John"));
        game.addPlayer(new Player("Pete"));
        assertTrue(game.isReady());
    }

    @Test
    public void addMaximumOfPlayers() {
        for (int i = 0; i < NokerGame.MAX_NUM_OF_PLAYERS; i++) {
            game.addPlayer(new Player("TestPlayer"));
        }
    }

    @Test(expected = ExceededMaxPlayersException.class)
    public void exceedMaximumOfPlayers() {
        for (int i = 0; i < NokerGame.MAX_NUM_OF_PLAYERS; i++) {
            game.addPlayer(new Player("TestPlayer"));
        }
        // An exception should be thrown here:
        game.addPlayer(new Player("TestPlayer"));
    }

    @Test
    public void distributeInitialChips() {
        game.addPlayer(new Player("John"));
        game.addPlayer(new Player("Pete"));
        game.start();
        game.getAllPlayers().forEach(p -> Assert.assertEquals(p.getChipsStack(), NokerGame.INITIAL_CHIPS));
    }

//    TODO: test when matchmaking is implemented
//    @Test
//    public void matchFirstPlayerRotation() {
//        game.addPlayer(new com.zuehlke.houseofcards.Player("John"));
//        game.addPlayer(new com.zuehlke.houseofcards.Player("Pete"));
//        game.addPlayer(new com.zuehlke.houseofcards.Player("Tom"));
//        => expect first player to be John
//        game.nextMatch();
//        => expect first player to be Pete
//        game.nextMatch();
//        => expect first player to be Tom
//        game.nextMatch();
//        => expect first player to be John again
//    }

}