package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.NokerDeck;
import com.zuehlke.hoc.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


public class MatchTest {

    private Match match;
    private PlayerNotifierAdapter notifier;
    private List<Player> players;

    private Player tobi;
    private Player miki;
    private Player riki;

    @Before
    public void setup() {
        notifier = Mockito.mock(PlayerNotifierAdapter.class);
        players = Utils.loadDummyPlayers();
        players.forEach(player -> player.setChipsStack(10));
        tobi = players.get(0);
        miki = players.get(1);
        riki = players.get(2);
        match = new Match(players, new NokerDeck(), notifier);
    }

    @Test
    public void startMatch(){
        match.startMatch();

        Mockito.verify(notifier).broadcastMatchStart(match);
        Mockito.verify(notifier).broadcastRoundStarts();
        Mockito.verify(notifier).askPlayerForAction(players.get(0).getName(), 0);
    }

    @Test
    public void nextMatchPlayersWithNoChipsShouldBeExcluded() {
        match.getMatchPlayers().get(0).setChipsStack(0);

        Match nextMatch = match.createNextMatch();
        assertEquals(players.size()-1, nextMatch.getMatchPlayers().size());
    }

    @Test
    public void nextMatchFirstPlayerRotation() {
        int numOfMatches = 5;
        Match nextMatch = match.createNextMatch();

        for (int i = 0; i < numOfMatches; i++) {
            assertEquals(players.get((i+1) % players.size()), nextMatch.getFirstPlayerInTurn());
            nextMatch = nextMatch.createNextMatch();
        }
        assertEquals(players.size(), nextMatch.getMatchPlayers().size());
    }

    @Test
    public void playerSetCall() {
        match.startMatch();
        Player player = tobi;

        match.playerSet(player, 0);

        Mockito.verify(notifier).broadcastPlayerCalled(player);
        Mockito.verify(notifier, never()).broadcastPlayerRaised(any(), anyLong());
        Mockito.verify(notifier, never()).broadcastPlayerFolded(player);
    }

    @Test
    public void playerSetRaise() {
        match.startMatch();
        match.getMatchPlayers().forEach(player -> player.setChipsStack(NokerGame.INITIAL_CHIPS));
        Player player = tobi;
        long raiseAmount = 10;

        match.playerSet(player, raiseAmount);

        Mockito.verify(notifier).broadcastPlayerRaised(player, raiseAmount);
        Mockito.verify(notifier, never()).broadcastPlayerCalled(player);
        Mockito.verify(notifier, never()).broadcastPlayerFolded(player);

        assertEquals(NokerGame.INITIAL_CHIPS-raiseAmount, match.getMatchPlayers().get(0).getChipsStack());
    }

    @Test
    public void playerFold() {
        match.startMatch();
        Player player = tobi;

        match.playerFold(player);

        Mockito.verify(notifier).broadcastPlayerFolded(player);
        Mockito.verify(notifier, never()).broadcastPlayerCalled(player);
        Mockito.verify(notifier, never()).broadcastPlayerRaised(any(), anyLong());
    }

    @Test
    public void firstRoundFinishes() {
        match.startMatch();
        match.getMatchPlayers().forEach(player -> player.setChipsStack(NokerGame.INITIAL_CHIPS));

        match.playerSet(tobi, 10);     // raise 10
        match.playerSet(miki, 10);    // call
        match.playerFold(riki);

        Mockito.verify(notifier).broadcastRoundFinished();
        Mockito.verify(notifier, never()).broadcastMatchFinished(any());
    }

    @Test
    public void firstRoundEndedButNotFinished() {
        match.startMatch();
        match.getMatchPlayers().forEach(player -> player.setChipsStack(NokerGame.INITIAL_CHIPS));

        match.playerSet(tobi, 10);  // raise 10
        match.playerSet(miki, 10);  // call
        match.playerSet(riki, 15);  // raise 15

        Mockito.verify(notifier, never()).broadcastRoundFinished();
    }

    @Test
    public void matchFinished() {
        match.startMatch();
        match.getMatchPlayers().forEach(player -> player.setChipsStack(NokerGame.INITIAL_CHIPS));

        match.playerSet(tobi, 10);  // raise 10
        match.playerSet(miki, 10);  // call
        match.playerFold(riki);

        match.playerSet(tobi, 0);   // call
        match.playerSet(miki, 0);   // call

        Mockito.verify(notifier, times(2)).broadcastRoundFinished();
        Mockito.verify(notifier).broadcastMatchFinished(any());
    }
}
