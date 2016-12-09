package com.zuehlke.hoc.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;


public class WinningStrategyTest {

    private List<Player> matchPlayers;
    private Bets bets;

    private Player tobi;
    private Player ricki;
    private Player trump;
    private Player mike;

    @Before
    public void setup() {
        matchPlayers = new ArrayList<>();
        bets = Mockito.mock(Bets.class);

        tobi = new Player("tobi");
        ricki = new Player("ricki");
        trump = new Player("trump");
        mike = new Player("mike");

        matchPlayers.add(tobi);
        matchPlayers.add(ricki);
        matchPlayers.add(trump);
        matchPlayers.add(mike);

        when(bets.playerHasFolded(tobi)).thenReturn(false);
        when(bets.playerHasFolded(ricki)).thenReturn(false);
        when(bets.playerHasFolded(trump)).thenReturn(false);
        when(bets.playerHasFolded(mike)).thenReturn(true);
    }


    @Test
    public void onlyOnePair() {
        matchPlayers.forEach(Player::cleanHand);

        tobi.addCard(11);
        tobi.addCard(13);

        ricki.addCard(13);
        ricki.addCard(14);

        trump.addCard(3);
        trump.addCard(3);

        mike.addCard(2);
        mike.addCard(2);

        List<Player> winners = WinningStrategy.winners(matchPlayers, bets);
        assertEquals(1, winners.size());
        assertEquals(trump, winners.get(0));
    }


    @Test
    public void multiplePairs() {
        matchPlayers.forEach(Player::cleanHand);
        tobi.addCard(12);
        tobi.addCard(12);

        ricki.addCard(12);
        ricki.addCard(12);

        trump.addCard(13);
        trump.addCard(13);

        mike.addCard(14);
        mike.addCard(13);

        matchPlayers.add(tobi);
        matchPlayers.add(ricki);
        matchPlayers.add(trump);
        matchPlayers.add(mike);

        List<Player> winners = WinningStrategy.winners(matchPlayers, bets);
        assertEquals(1, winners.size());
        assertTrue(winners.contains(trump));
    }


    @Test
    public void multipleWinners() {
        matchPlayers.forEach(Player::cleanHand);
        tobi.addCard(14);
        tobi.addCard(13);

        ricki.addCard(13);
        ricki.addCard(14);

        trump.addCard(2);
        trump.addCard(3);

        mike.addCard(2);
        mike.addCard(2);

        List<Player> winners = WinningStrategy.winners(matchPlayers, bets);
        assertEquals(2, winners.size());
        assertTrue(winners.contains(tobi));
        assertTrue(winners.contains(ricki));
    }


    @Test
    public void highestSecondCard() {
        matchPlayers.forEach(Player::cleanHand);
        tobi.addCard(12);
        tobi.addCard(11);

        ricki.addCard(13);
        ricki.addCard(11);

        trump.addCard(13);
        trump.addCard(12);

        mike.addCard(2);
        mike.addCard(2);

        List<Player> winners = WinningStrategy.winners(matchPlayers, bets);
        assertEquals(1, winners.size());
        assertTrue(winners.contains(trump));
    }
}