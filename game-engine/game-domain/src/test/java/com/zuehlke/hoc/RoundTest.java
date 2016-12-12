package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Deck;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.model.Round;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;


public class RoundTest {

    private List<Player> players;
    private Deck deck;
    private NokerGameObserverAdapter notifier;
    private Player tobi;
    private Player miki;
    private Player riki;


    @Before
    public void beforeTest(){
        players = players();
        deck = deck();
        notifier = Mockito.mock(NokerGameObserverAdapter.class);
        tobi = players.get(0);
        miki = players.get(1);
        riki = players.get(2);
    }

    @Test
    public void startRounds(){
        Round round = new Round(players, 0, deck, notifier);

        round.startRound();

        assertTrue(tobi.getFirstCard().isPresent());
        assertTrue(miki.getFirstCard().isPresent());
        assertTrue(riki.getFirstCard().isPresent());

        assertFalse(tobi.getSecondCard().isPresent());
        assertFalse(miki.getSecondCard().isPresent());
        assertFalse(riki.getSecondCard().isPresent());

        tobi.getFirstCard().ifPresent(card -> assertEquals(1, card.intValue()));
        miki.getFirstCard().ifPresent(card -> assertEquals(2, card.intValue()));
        riki.getFirstCard().ifPresent(card -> assertEquals(3, card.intValue()));

        round = round.createSecondRound();
        round.startRound();

        tobi.getFirstCard().ifPresent(card -> assertEquals(1, card.intValue()));
        miki.getFirstCard().ifPresent(card -> assertEquals(2, card.intValue()));
        riki.getFirstCard().ifPresent(card -> assertEquals(3, card.intValue()));


        tobi.getSecondCard().ifPresent(card -> assertEquals(4, card.intValue()));
        miki.getSecondCard().ifPresent(card -> assertEquals(5, card.intValue()));
        riki.getSecondCard().ifPresent(card -> assertEquals(6, card.intValue()));
    }

    @Test
    public void startVerifyNotificationCall(){

        Round round = new Round(players, 0, deck, notifier);

        round.startRound();

        InOrder inOrder = Mockito.inOrder(notifier);

        inOrder.verify(notifier).broadcastRoundStarts();
        inOrder.verify(notifier).sendCardInfoToPlayer(tobi.getName(), 1);
        inOrder.verify(notifier).sendCardInfoToPlayer(miki.getName(), 2);
        inOrder.verify(notifier).sendCardInfoToPlayer(riki.getName(), 3);
        inOrder.verify(notifier).askPlayerForAction(eq(tobi), anyLong(), anyLong(), anyLong(), any());

        round = round.createSecondRound();
        round.startRound();

        inOrder.verify(notifier).broadcastRoundStarts();
        inOrder.verify(notifier).sendCardInfoToPlayer(tobi.getName(), 4);
        inOrder.verify(notifier).sendCardInfoToPlayer(miki.getName(), 5);
        inOrder.verify(notifier).sendCardInfoToPlayer(riki.getName(), 6);
        inOrder.verify(notifier).askPlayerForAction(eq(tobi), anyLong(), anyLong(), anyLong(), any());
    }

    @Test
    public void playOneRoundWithOneRaise(){

        Round round = new Round(players, 0, deck, notifier);
        round.startRound();

        InOrder inOrder = Mockito.inOrder(notifier);
        players.forEach(player -> round.getBets().withdrawBlindFromPlayer(player));
        inOrder.verify(notifier).broadcastRoundStarts();
        inOrder.verify(notifier).sendCardInfoToPlayer(tobi.getName(), 1);
        inOrder.verify(notifier).sendCardInfoToPlayer(miki.getName(), 2);
        inOrder.verify(notifier).sendCardInfoToPlayer(riki.getName(), 3);
        inOrder.verify(notifier).askPlayerForAction(eq(tobi), anyLong(), anyLong(), anyLong(), any());

        round.playerCall(tobi);
        inOrder.verify(notifier).askPlayerForAction(eq(miki), anyLong(), anyLong(), anyLong(), any());
        round.playerCall(miki);
        inOrder.verify(notifier).askPlayerForAction(eq(riki), anyLong(), anyLong(), anyLong(), any());
        round.playerRaise(riki, 2);
        inOrder.verify(notifier).askPlayerForAction(eq(tobi), anyLong(), anyLong(), anyLong(), any());
        round.playerCall(tobi);
        inOrder.verify(notifier).askPlayerForAction(eq(miki), anyLong(), anyLong(), anyLong(), any());
        round.playerFold(miki);
        inOrder.verify(notifier).broadcastRoundFinished();

        assertEquals(43, tobi.getChipsStack());
        assertEquals(100, miki.getChipsStack());
        assertEquals(143, riki.getChipsStack());
    }

    @Test
    public void playOneRoundWithOneReRaise(){

        Round round = new Round(players, 0, deck, notifier);
        round.startRound();

        InOrder inOrder = Mockito.inOrder(notifier);
        players.forEach(player -> round.getBets().withdrawBlindFromPlayer(player));
        inOrder.verify(notifier).broadcastRoundStarts();
        inOrder.verify(notifier).sendCardInfoToPlayer(tobi.getName(), 1);
        inOrder.verify(notifier).sendCardInfoToPlayer(miki.getName(), 2);
        inOrder.verify(notifier).sendCardInfoToPlayer(riki.getName(), 3);
        inOrder.verify(notifier).askPlayerForAction(eq(tobi), anyLong(), anyLong(), anyLong(), any());

        round.playerCall(tobi);
        inOrder.verify(notifier).askPlayerForAction(eq(miki), anyLong(), anyLong(), anyLong(), any());
        round.playerRaise(miki, 1);
        inOrder.verify(notifier).askPlayerForAction(eq(riki), anyLong(), anyLong(), anyLong(), any());
        round.playerRaise(riki, 2);
        inOrder.verify(notifier).askPlayerForAction(eq(tobi), anyLong(), anyLong(), anyLong(), any());
        round.playerFold(tobi);
        inOrder.verify(notifier).askPlayerForAction(eq(miki), anyLong(), anyLong(), anyLong(), any());
        round.playerCall(miki);
        inOrder.verify(notifier).broadcastRoundFinished();

        assertEquals(50, tobi.getChipsStack());
        assertEquals(93, miki.getChipsStack());
        assertEquals(143, riki.getChipsStack());
    }

    private List<Player> players() {
        List<Player> players = Arrays.asList(new Player("Tobi"), new Player("Miki"), new Player("Riki"));
        players.get(0).setChipsStack(50);
        players.get(1).setChipsStack(100);
        players.get(2).setChipsStack(150);
        return players;
    }

    private Deck deck() {
        return new Deck() {

            int i = 0;

            @Override
            public int drawCard() {
                i++;
                return i;
            }

            @Override
            public void shuffle() {
                i = 0;
            }
        };
    }
}
