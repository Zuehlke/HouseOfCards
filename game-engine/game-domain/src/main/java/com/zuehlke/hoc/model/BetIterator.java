package com.zuehlke.hoc.model;

import java.util.Iterator;
import java.util.List;

/**
 * The {@link BetIterator} is a round robin iterator which is used to get the next player in a {@link Round}.
 *
 * - it starts at the given index
 * - it round robs until all player have a even bet or had folded
 */
public class BetIterator implements Iterator<Player> {

    private final Bets bets;
    private final List<Player> players;

    private int index = 0;
    private int startIndex;


    public BetIterator(List<Player> players, int startIndex, Bets bets) {
        this.players = players;
        this.index = startIndex;
        this.startIndex = startIndex;
        this.bets = bets;
    }

    public static int nextPlayerIndex(List<Player> players, int index){
        return (index + 1) % players.size();
    }


    /**
     * Resets the index of the {@link BetIterator} to initial index.
     */
    public void reset(){
        index = startIndex;
    }

    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public boolean hasNext() {
        for(Player player : players) {
            if(!hasFoldedOrCalled(player)) {
                return true;
            }
        }
        return false;
    }

    // check if there is a player which hasn't fold or called (so all player despite
    // the one which have folded has a even bet in the pot)
    private boolean hasFoldedOrCalled(Player player) {
        return bets.playerHasFolded(player) || bets.playerHasCalled(player);
    }

    @Override
    public Player next() {
        int startIndex = index;
        Player res = players.get(index);
        while(hasFoldedOrCalled(res)){
            index = (index + 1) % players.size();
            res = players.get(index);
            if(index == startIndex){
                throw new RuntimeException("No next player!");
            }
        }
        index = (index + 1) % players.size();
        return res;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
