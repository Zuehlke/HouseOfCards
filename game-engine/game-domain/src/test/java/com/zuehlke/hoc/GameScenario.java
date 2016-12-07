package com.zuehlke.hoc;


import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.PlayerNotifier;
import com.zuehlke.hoc.notification.api.StartInfo;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This class demonstrates a game scenario and
 * shows the interaction between players and the game domain.
 */
public class GameScenario {

    private static Logger log = LoggerFactory.getLogger("PlayerNotifierLogger");

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

    @Test
    public void testMatchStarted() {
        PlayerNotifier playerNotifier = new PlayerNotifier() {
            @Override
            public void sendCardInfo(String player, int card) {
                log.info("called sendCardInfo");
            }

            @Override
            public void playersTurn(String player, long minimumChipsForCall) {
                log.info("called playersTurn");
            }

            @Override
            public void broadcastGameStarts(StartInfo info) {
                log.info("called broadcastGameStarts");
            }

            @Override
            public void broadcastPlayerRaised(String playerName, long amount) {
                log.info("called broadcastPlayerRaised");
            }

            @Override
            public void broadcastPlayerCalled(String playerName) {
                log.info("called broadcastPlayerCalled");
            }

            @Override
            public void broadcastPlayerFolded(String playerName) {
                log.info("called broadcastPlayerFolded");
            }

            @Override
            public void broadcastNextRound() {
                log.info("called broadcastNext Round");
            }

            @Override
            public void broadcastNextMatch() {
                log.info("called broadcastNextMatch");
            }
        };


        NokerGame game = new NokerGame(3, playerNotifier);

        Player tobi = game.createPlayer("tobi");
        Player riki = game.createPlayer("riki");
        Player miki = game.createPlayer("miki");

    }
}
