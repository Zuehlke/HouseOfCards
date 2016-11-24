package com.zuehlke;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;


public class MatchTest {

    private Match match;
    private Deck mockedDeck;
    private List<Player> players;
    private int cardCounter = 0;

    @Before
    public void setup() {
        mockedDeck = mock(Deck.class);
        Mockito.doAnswer(invocation -> cardCounter++).when(mockedDeck).drawCard();

        players = new ArrayList<>();
        players.add(new Player("John"));
        players.add(new Player("Pete"));
        players.add(new Player("Tom"));
        match = new com.zuehlke.Match(players, mockedDeck);
    }

    @Test
    public void dealFirstCardPlayersGetCorrectCard() {
        match.dealFirstCard();
        for (int i = 0; i < match.getAllPlayers().size(); i++) {
             assertEquals(match.getAllPlayers().get(i).getFirstCard(), i);
        }
    }

    @Test
    public void dealFirstCardDeckIsUpdated() {
        Deck deck = new Deck();
        deck.initialize();
        deck.shuffle();

        Match testMatch = new Match(players, deck);
        testMatch.dealFirstCard();

        assertEquals(testMatch.getDeck().getSize(), Deck.NUM_CARDS_OF_SINGLE_DECK - players.size());
    }
}