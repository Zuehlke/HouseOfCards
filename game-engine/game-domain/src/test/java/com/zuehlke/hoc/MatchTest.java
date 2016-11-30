package com.zuehlke.hoc;

import com.zuehlke.hoc.model.Match;
import com.zuehlke.hoc.model.NokerDeck;
import com.zuehlke.hoc.model.Player;
import com.zuehlke.hoc.notification.api.PlayerNotifier;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MatchTest {

    private Match match;
    private PlayerNotifierAdapter notifier;
    private List<Player> players;

    @Before
    public void setup() {
        notifier = Mockito.mock(PlayerNotifierAdapter.class);
        players = getDummyPlayers();
        match = new Match(players, new NokerDeck(), notifier);
    }

    @Test
    public void startMatch(){
        match.startMatch();

        Mockito.verify(notifier).broadcastMatchStart(match);
        Mockito.verify(notifier).broadcastRoundStarts();
        Mockito.verify(notifier).askPlayerForAction(getDummyPlayers().get(0).getName(), 0);
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


    private List<Player> getDummyPlayers() {
        List<Player> players = Arrays.asList(new Player("Tobi"), new Player("Miki"), new Player("Riki"));
        players.forEach(p -> p.setChipsStack(10));
        return players;
    }
}
