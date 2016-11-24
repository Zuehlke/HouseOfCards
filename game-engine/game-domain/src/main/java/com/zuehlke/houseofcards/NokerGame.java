package com.zuehlke.houseofcards;

import com.zuehlke.houseofcards.Exceptions.ExceededMaxPlayersException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class NokerGame implements Game {

    public final static long INITIAL_CHIPS = 100;
    public final static int MIN_NUM_OF_PLAYERS = 2;
    public final static int CARDS_PER_PLAYER = 2;
    public static final int MAX_NUM_OF_PLAYERS = Deck.NUM_CARDS_OF_SINGLE_DECK/CARDS_PER_PLAYER;

    private List<Player> players = new ArrayList<>();
    private Deck deck = new Deck();
    private int firstPlayerPosition = 0;

    private Match match;


    public void addPlayer(Player player) {
        if (players.size() < MAX_NUM_OF_PLAYERS) {
            players.add(player);
        } else {
            throw new ExceededMaxPlayersException("Could not add player. Exceeded the maximum number of players.");
        }
    }

    public boolean isReady() {
        return players.size() >= MIN_NUM_OF_PLAYERS;
    }

    public List<Player> getAllPlayers() {
        return new ArrayList<>(players);
    }

    public void start() {
        players.forEach(p -> p.setChipsStack(INITIAL_CHIPS));
        match = new Match(getRotatedPlayersForNextMatch(), deck);
        firstPlayerPosition = RoundRobin.getNextStartPosition(players,firstPlayerPosition);
        match.dealFirstCard();
    }

    @Override
    public void handleAction(Action action) {
        // TODO: implement method
    }

    /**
     * Rearranges the player order for the next match.
     * The turn order of the players rotates every time a new match is started.
     * @return list with rotated players
     */
    private List<Player> getRotatedPlayersForNextMatch() {
        List<Player> rotatedPlayers = new ArrayList<>();
        RoundRobin<Player> playerRoundRobin = new RoundRobin<>(this.players, firstPlayerPosition);
        Iterator<Player> playerIterator = playerRoundRobin.iterator();

        while (playerIterator.hasNext()){
            rotatedPlayers.add(playerIterator.next());
        }
        return rotatedPlayers;
    }
}
