package com.zuehlke.hoc;


import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.PlayerNotifier;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * This class demonstrates a game scenario and
 * shows the interaction between players and the game domain.
 */
public class GameScenario {

    @Test
    public void demo(){

        PlayerNotifier playerNotifier = Mockito.mock(PlayerNotifier.class);

        NokerGame game = new NokerGame(3, playerNotifier);

        Player tobi = game.createPlayer("tobi");
        Player riki = game.createPlayer("riki");
        Player trump = game.createPlayer("trump");
        // all players registered => game is started


        // first match

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

        // first round
        game.playerRaise(riki, 10);
        game.playerFold(trump);
        game.playerCall(tobi);

        // second round
        game.playerRaise(riki, 20);
        game.playerCall(tobi);

        // ...
    }
}
