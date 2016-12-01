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
        Player miki = game.createPlayer("miki");
        // all players registered => game is started


        // first match

        // first round
        game.playerSet(tobi, 0);    // check = call
        game.playerSet(riki, 0);    // check = call
        game.playerSet(miki, 10);   // raise 10
        game.playerSet(tobi, 10);   // call
        game.playerFold(riki);

        //second round
        game.playerSet(tobi, 20);   // raise 20
        game.playerSet(miki, 40);   // raise 20 (call: 20 + raise: 20 = 40)
        game.playerSet(tobi, 20);   // call

        // second match

        // first round
        game.playerSet(riki, 10);   // raise 10
        game.playerFold(miki);
        game.playerSet(tobi, 10);   // call


        // second round
        game.playerSet(riki, 20);   // raise 20
        game.playerSet(tobi, 20);   // call

        // ...
    }
}
