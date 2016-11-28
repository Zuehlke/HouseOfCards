package com.zuehlke.hoc;

import com.zuehlke.hoc.model.BetIterator;
import com.zuehlke.hoc.model.Bets;
import com.zuehlke.hoc.model.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by bolli on 27.11.2016.
 */
public class BetsIteratorTest {

    public static final String TOBI = "Tobi";
    public static final String MIKI = "Miki";
    public static final String RIKI = "Riki";


    private BetIterator betIterator;
    private List<Player> players;
    //private List<Player> foldedPlayer = new ArrayList<>();
    //private List<Player> calledPlayer = new ArrayList<>();
    private Bets internalBets;


    @Rule
    public ExpectedException thrown = ExpectedException.none();


    void init(int startIndex){
        players = players();
        internalBets = mock(Bets.class);
        betIterator = new BetIterator(players,startIndex, internalBets);
    }



    @Test
    public void roundRobinWithoutBets(){

        init(1);

        assertRightPlayer(MIKI, betIterator.next());
        assertRightPlayer(RIKI, betIterator.next());
        assertRightPlayer(TOBI, betIterator.next());

        //T->M->R->T->M (5 steps)
        betIterator.next();
        betIterator.next();
        betIterator.next();
        assertRightPlayer(MIKI, betIterator.next());

    }

    @Test
    public void roundRobinWithFoldedPlayer(){
        //act: MIKI
        init(1);

        //MIKI folds
        when(internalBets.playerHasCalled(players.get(1))).thenReturn(true);
        assertTrue(betIterator.hasNext());
        assertRightPlayer(RIKI, betIterator.next());

        //RIKI folds
        when(internalBets.playerHasCalled(players.get(2))).thenReturn(true);
        assertTrue(betIterator.hasNext());
        assertRightPlayer(TOBI, betIterator.next());
        assertRightPlayer(TOBI, betIterator.next());

        //TOBI folds
        when(internalBets.playerHasCalled(players.get(0))).thenReturn(true);
        assertFalse(betIterator.hasNext());

        expected(RuntimeException.class, "No next player!");
        betIterator.next();
    }


    @Test
    public void roundScenario(){
        //act: MIKI
        init(1);

        //MIKI's trun he has checked (calls 0)
        assertTrue(betIterator.hasNext());
        assertRightPlayer(MIKI, betIterator.next());
        setupCalledMock(false, true, false);


        //RIKI's trun he has raised
        assertTrue(betIterator.hasNext());
        assertRightPlayer(RIKI, betIterator.next());
        setupCalledMock(false, false, true);

        //TOBI's trun he has folded
        assertTrue(betIterator.hasNext());
        assertRightPlayer(TOBI, betIterator.next());
        setupFoldedMock(true,false,false);

        //MIKI's trun he has called
        assertTrue(betIterator.hasNext());
        assertRightPlayer(MIKI, betIterator.next());
        setupCalledMock(false,true,true);

        //No ones turn
        assertFalse(betIterator.hasNext());

    }

    private void setupCalledMock(Boolean tobi, Boolean miki, Boolean riki) {
        when(internalBets.playerHasCalled(players.get(0))).thenReturn(tobi);
        when(internalBets.playerHasCalled(players.get(1))).thenReturn(miki);
        when(internalBets.playerHasCalled(players.get(2))).thenReturn(riki);
    }

    private void setupFoldedMock(Boolean tobi, Boolean miki, Boolean riki) {
        when(internalBets.playerHasFolded(players.get(0))).thenReturn(tobi);
        when(internalBets.playerHasFolded(players.get(1))).thenReturn(miki);
        when(internalBets.playerHasFolded(players.get(2))).thenReturn(riki);
    }



    private void assertRightPlayer(String exp, Player act){
        assertEquals(exp, act.getName());
    }

    private void expected(Class<RuntimeException> ex, String msg) {
        thrown.expect(ex);
        thrown.expectMessage(msg);
    }


    private List<Player> players() {
        List<Player> players = Arrays.asList(new Player(TOBI), new Player(MIKI), new Player(RIKI));
        players.get(0).setChipsStack(5);
        players.get(1).setChipsStack(10);
        players.get(2).setChipsStack(15);
        return players;
    }

    private class BetsMock extends Bets {

        private final List<Player> foldedPlayer;
        private final List<Player> calledPlayer;

        public BetsMock(List<Player> foldedPlayer, List<Player> calledPlayer) {
            super();
            this.foldedPlayer = foldedPlayer;
            this.calledPlayer = calledPlayer;
        }

        @Override
        public boolean playerHasFolded(Player player) {
            return foldedPlayer.contains(player);
        }

        @Override
        public boolean playerHasCalled(Player player) {
            return calledPlayer.contains(player);
        }
    }
}
