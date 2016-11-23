package com.zuehlke;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class MatchTest {

    private Match match;
    private List<Player> players;

    // TODO: implement dummy deck returns a counter
    // => this way we can check if the correct players get cards in the correct order from the deck

    @Before
    public void setup() {
        players = new ArrayList<>();
        players.add(new Player("John"));
        players.add(new Player("Pete"));
        players.add(new Player("Tom"));
        // TODO: match = new com.zuehlke.Match(players, mockDeck);
    }

//    @Test
//    public void dealFirstCard() {
//        match.dealFirstCard();
//        players.forEach(p -> {
//            assertNotNull(p.getFirstCard());
//            // TODO: check that each player got the corresponding card from the deck
//        });
//    }
}