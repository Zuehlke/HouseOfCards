package com.zuehlke.hoc;

import com.zuehlke.hoc.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class RoundTest {

    private List<Player> players;
    private Deck deck;
    private PlayerNotifierAdapter notifier;
    private Player tobi;
    private Player miki;
    private Player riki;


    @Before
    public void beforeTest(){
        players = players();
        deck = deck();
        notifier = Mockito.mock(PlayerNotifierAdapter.class);
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
        inOrder.verify(notifier).askPlayerForAction(tobi.getName(), 0, 0);

        round = round.createSecondRound();
        round.startRound();

        inOrder.verify(notifier).broadcastRoundStarts();
        inOrder.verify(notifier).sendCardInfoToPlayer(tobi.getName(), 4);
        inOrder.verify(notifier).sendCardInfoToPlayer(miki.getName(), 5);
        inOrder.verify(notifier).sendCardInfoToPlayer(riki.getName(), 6);
        inOrder.verify(notifier).askPlayerForAction(players().get(0).getName(), 0, 0);
    }

    @Test
    public void playOneRoundWithOneRaise(){

        Round round = new Round(players, 0, deck, notifier);
        round.startRound();

        InOrder inOrder = Mockito.inOrder(notifier);
        inOrder.verify(notifier).broadcastRoundStarts();
        inOrder.verify(notifier).sendCardInfoToPlayer(tobi.getName(), 1);
        inOrder.verify(notifier).sendCardInfoToPlayer(miki.getName(), 2);
        inOrder.verify(notifier).sendCardInfoToPlayer(riki.getName(), 3);
        inOrder.verify(notifier).askPlayerForAction(tobi.getName(), 0, 0);

        round.playerCall(tobi);
        inOrder.verify(notifier).askPlayerForAction(miki.getName(), 0, 0);
        round.playerCall(miki);
        inOrder.verify(notifier).askPlayerForAction(riki.getName(), 0, 0);
        round.playerRaise(riki, 2);
        inOrder.verify(notifier).askPlayerForAction(tobi.getName(), 2, 0);
        round.playerCall(tobi);
        inOrder.verify(notifier).askPlayerForAction(miki.getName(), 2, 0);
        round.playerFold(miki);
        inOrder.verify(notifier).broadcastRoundFinished();

        assertEquals(3, tobi.getChipsStack());
        assertEquals(10, miki.getChipsStack());
        assertEquals(13, riki.getChipsStack());
    }

    @Test
    public void playOneRoundWithOneReRaise(){

        Round round = new Round(players, 0, deck, notifier);
        round.startRound();

        InOrder inOrder = Mockito.inOrder(notifier);
        inOrder.verify(notifier).broadcastRoundStarts();
        inOrder.verify(notifier).sendCardInfoToPlayer(tobi.getName(), 1);
        inOrder.verify(notifier).sendCardInfoToPlayer(miki.getName(), 2);
        inOrder.verify(notifier).sendCardInfoToPlayer(riki.getName(), 3);
        inOrder.verify(notifier).askPlayerForAction(tobi.getName(), 0, 0);

        round.playerCall(tobi);
        inOrder.verify(notifier).askPlayerForAction(miki.getName(), 0, 0);
        round.playerRaise(miki, 1);
        inOrder.verify(notifier).askPlayerForAction(riki.getName(), 1, 0);
        round.playerRaise(riki, 2);
        inOrder.verify(notifier).askPlayerForAction(tobi.getName(), 3, 0);
        round.playerFold(tobi);
        inOrder.verify(notifier).askPlayerForAction(miki.getName(), 2, 0);
        round.playerCall(miki);
        inOrder.verify(notifier).broadcastRoundFinished();

        assertEquals(5, tobi.getChipsStack());
        assertEquals(7, miki.getChipsStack());
        assertEquals(12, riki.getChipsStack());
    }

    private List<Player> players() {
        List<Player> players = Arrays.asList(new Player("Tobi"), new Player("Miki"), new Player("Riki"));
        players.get(0).setChipsStack(5);
        players.get(1).setChipsStack(10);
        players.get(2).setChipsStack(15);
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
