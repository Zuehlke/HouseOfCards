package com.zuehlke.hoc;


import com.zuehlke.hoc.model.Bets;
import com.zuehlke.hoc.model.Player;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BetsTest {

    private List<Player> players;
    private Player tobi;
    private Player miki;
    private Player riki;

    private Bets bets;


    @Before
    public void setup(){
        bets = new Bets();
        players = Utils.loadDummyPlayers();

        tobi = players.get(0);
        miki = players.get(1);
        riki = players.get(2);

        tobi.setChipsStack(50);
        miki.setChipsStack(100);
        riki.setChipsStack(150);
    }


    @Test
    public void playerFolds(){
        bets.withdrawBlindFromPlayer(tobi);
        bets.playerFolds(tobi);
        assertEquals(45, tobi.getChipsStack());
        assertEquals(5, bets.getTotalPot());
    }


    // A check move refers to a call move in a round where no other
    // player has raised before, therefore the amount of chips to call is 0.
    @Test
    public void playerChecks(){
        bets.withdrawBlindFromPlayer(tobi);
        bets.playerCalls(tobi);
        assertEquals(50, tobi.getChipsStack());
        assertEquals(0, bets.getTotalPot());
    }

    @Test
    public void firstRaise(){
        bets.withdrawBlindFromPlayer(tobi);
        bets.playerRaise(tobi, 2);
        assertEquals(43, tobi.getChipsStack());
        assertEquals(7, bets.getTotalPot());
    }

    @Test
    public void callAfterRaise(){
        bets.withdrawBlindFromPlayer(tobi);
        bets.withdrawBlindFromPlayer(miki);
        bets.playerRaise(tobi, 2);
        bets.playerCalls(miki);
        assertEquals(93, miki.getChipsStack());
        assertEquals(14, bets.getTotalPot());
    }

    // TODO: rewrite test
//    @Test
//    public void toLessChipsToCall(){
//        bets.playerRaise(miki, 8);
//        expected(RuntimeException.class,"Player has to less chips!");
//        bets.playerCalls(tobi);
//    }

    @Test
    public void reRaise(){
        bets.withdrawBlindFromPlayer(tobi);
        bets.withdrawBlindFromPlayer(miki);
        bets.playerRaise(tobi, 2);
        bets.playerRaise(miki, 2);
        assertEquals(43, tobi.getChipsStack());
        assertEquals(93, miki.getChipsStack());
        assertEquals(14, bets.getTotalPot());
    }

    @Test
    public void playerHasCalled(){
        bets.withdrawBlindFromPlayer(miki);
        bets.withdrawBlindFromPlayer(tobi);
        bets.withdrawBlindFromPlayer(riki);

        bets.playerCalls(tobi);
        assertTrue(bets.playerHasCalled(tobi));

        bets.playerFolds(miki);
        assertFalse(bets.playerHasCalled(miki));

        assertFalse(bets.playerHasCalled(riki));
    }

    @Test
    public void playerHasFolded(){
        bets.withdrawBlindFromPlayer(miki);
        bets.withdrawBlindFromPlayer(tobi);
        bets.withdrawBlindFromPlayer(riki);

        bets.playerFolds(miki);
        assertTrue(bets.playerHasFolded(miki));

        bets.playerCalls(tobi);
        assertFalse(bets.playerHasFolded(tobi));

        assertFalse(bets.playerHasFolded(riki));
    }


    @Test
    public void startNextBetRound(){
        bets.withdrawBlindFromPlayer(miki);
        bets.withdrawBlindFromPlayer(tobi);
        bets.withdrawBlindFromPlayer(riki);

        bets.playerFolds(miki);
        bets.playerCalls(tobi);
        bets.playerCalls(riki);

        bets.startNextBetRound();

        assertTrue(bets.playerHasFolded(miki));
        assertFalse(bets.playerHasFolded(tobi));
        assertFalse(bets.playerHasFolded(riki));

        assertFalse(bets.playerHasCalled(miki));
        assertFalse(bets.playerHasCalled(tobi));
        assertFalse(bets.playerHasCalled(riki));
    }
}
