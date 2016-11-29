package com.zuehlke.hoc;


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


//    @Test(expected = InitGameException.class)
//    public void initGameWithTooLittlePlayers() {
//        Game game = new NokerGame(1, null);
//    }
//
//    @Test(expected = InitGameException.class)
//    public void initGameWithZeroPlayers() {
//        Game game = new NokerGame(0, null);
//    }
//
//    @Test
//    public void initGameWithMaxPlayers() {
//        Game game = new NokerGame(NokerGame.MAX_NUM_OF_PLAYERS, null);
//        State gameState = null;
//        for (int i = 0; i < NokerGame.MAX_NUM_OF_PLAYERS; i++) {
//            gameState = game.addPlayer(new Player("TestPlayer"));
//        }
//        assertEquals(gameState.getRegisteredPlayers().size(), NokerGame.MAX_NUM_OF_PLAYERS);
//    }
//
//    @Test(expected = InitGameException.class)
//    public void initGameWithTooManyPlayers() {
//        Game game = new NokerGame(NokerGame.MAX_NUM_OF_PLAYERS + 1, null);
//    }
//
//    @Test
//    public void addPlayers() {
//        int numOfPlayers = 3;
//        Game game = new NokerGame(numOfPlayers);
//
//        game.addPlayer(new Player("TestPlayer1"));
//        assertFalse(game.isReady());
//        game.addPlayer(new Player("TestPlayer2"));
//        assertFalse(game.isReady());
//        game.addPlayer(new Player("TestPlayer3"));
//        assertTrue(game.isReady());
//
//        // The following players should not be added to the game.
//        game.addPlayer(new Player("TestPlayer4"));
//        State gameState = game.addPlayer(new Player("TestPlayer5"));
//
//        assertTrue(game.isReady());
//        assertEquals(gameState.getRegisteredPlayers().size(), numOfPlayers);
//    }
//
//
//    @Test
//    public void distributeInitialChips() {
//        Game game = new NokerGame(2);
//        game.addPlayer(new Player("John"));
//        State gameState = game.addPlayer(new Player("Pete"));
//
//        gameState.getRegisteredPlayers()
//                .forEach(player -> assertEquals(player.getChipsStack(), NokerGame.INITIAL_CHIPS));
//    }




//    TODO: test when matchmaking is implemented
//    @Test
//    public void matchFirstPlayerRotation() {
//        game.createPlayer(new com.zuehlke.houseofcards.model.Player("John"));
//        game.createPlayer(new com.zuehlke.houseofcards.model.Player("Pete"));
//        game.createPlayer(new com.zuehlke.houseofcards.model.Player("Tom"));
//        => expect first player to be John
//        game.nextMatch();
//        => expect first player to be Pete
//        game.nextMatch();
//        => expect first player to be Tom
//        game.nextMatch();
//        => expect first player to be John again
//    }

//    @Test
//    public void dealFirstCardPlayersGetCorrectCard() {
//        match.dealFirstCard();
//        for (int i = 0; i < match.getAllPlayers().size(); i++) {
//            assertEquals(match.getAllPlayers().get(i).getFirstCard(), i);
//        }
//    }
//
//    @Test
//    public void dealFirstCardDeckIsUpdated() {
//        Deck deck = new Deck();
//        Match testMatch = new Match(players, deck);
//        testMatch.dealFirstCard();
//        assertEquals(Deck.NUM_CARDS_OF_SINGLE_DECK - players.size(), testMatch.getDeck().getSize());
//    }
}