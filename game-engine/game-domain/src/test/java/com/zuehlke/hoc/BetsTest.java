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
    private Bets bets;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void before(){
        players = players();
        bets = new Bets();
    }


    @Test
    public void folds(){
        bets.playerFolds(players.get(0));
        assertEquals(players.get(0).getChipsStack(), 5);
        assertEquals(0,bets.getTotalPot());
    }

    @Test
    public void alreadyFolded(){
        bets.playerFolds(players.get(0));
        expected(RuntimeException.class,"Player has already folded!");
        bets.playerCalls(players.get(0));
    }

    @Test
    public void callsNoBets(){
        bets.playerCalls(players.get(0));
        assertEquals(players.get(0).getChipsStack(), 5);
        assertEquals(0,bets.getTotalPot());
    }

    @Test
    public void testFirstRaise(){
        bets.playerRaise(players.get(0), 2);
        assertEquals(3, players.get(0).getChipsStack());
        assertEquals(2,bets.getTotalPot());
    }

    @Test
    public void toLessChipsToRaise(){
        expected(RuntimeException.class,"Player has to less chips!");
        bets.playerRaise(players.get(0), 10);
    }

    @Test
    public void callAfterRaise(){
        bets.playerRaise(players.get(0), 2);
        bets.playerCalls(players.get(1));
        assertEquals(8, players.get(1).getChipsStack());
        assertEquals(4,bets.getTotalPot());
    }

    @Test
    public void toLessChipsToCall(){
        bets.playerRaise(players.get(1), 8);
        expected(RuntimeException.class,"Player has to less chips!");
        bets.playerCalls(players.get(0));
    }

    @Test
    public void reRaise(){
        bets.playerRaise(players.get(0), 2);
        bets.playerRaise(players.get(1), 2);
        assertEquals(3,players.get(0).getChipsStack());
        assertEquals(6, players.get(1).getChipsStack());
        assertEquals(6,bets.getTotalPot());
    }

    @Test
    public void playerHasCalled(){
        bets.playerCalls(players.get(0));
        assertTrue(bets.playerHasCalled(players.get(0)));

        bets.playerFolds(players.get(1));
        assertFalse(bets.playerHasCalled(players.get(1)));

        assertFalse(bets.playerHasCalled(players.get(2)));
    }

    @Test
    public void playerHasFolded(){

        bets.playerFolds(players.get(1));
        assertTrue(bets.playerHasFolded(players.get(1)));

        bets.playerCalls(players.get(0));
        assertFalse(bets.playerHasFolded(players.get(0)));

        assertFalse(bets.playerHasFolded(players.get(2)));
    }


    @Test
    public void startNextBetRound(){

        bets.playerFolds(players.get(1));
        bets.playerCalls(players.get(0));
        bets.playerCalls(players.get(2));

        bets.startNextBetRound();

        assertTrue(bets.playerHasFolded(players.get(1)));
        assertFalse(bets.playerHasFolded(players.get(0)));
        assertFalse(bets.playerHasFolded(players.get(2)));

        assertFalse(bets.playerHasCalled(players.get(1)));
        assertFalse(bets.playerHasCalled(players.get(0)));
        assertFalse(bets.playerHasCalled(players.get(2)));



    }



    private void expected(Class<RuntimeException> ex, String msg) {
        thrown.expect(ex);
        thrown.expectMessage(msg);
    }

    private List<Player> players() {
        List<Player> players = Arrays.asList(new Player("Tobi"), new Player("Miki"), new Player("Riki"));
        players.get(0).setChipsStack(5);
        players.get(1).setChipsStack(10);
        players.get(2).setChipsStack(15);
        return players;
    }
}
