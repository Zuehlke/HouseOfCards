package com.zuehlke.houseofcards.model;

import com.zuehlke.houseofcards.PlayerNotifierAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a match of a game.
 * A match consists of two consecutive rounds of betting.
 */
public class Match {

    private List<Player> matchPlayers;
    private boolean firstRound = true;
    private int firstPlayerIndexInRound;
    private Round round;
    private Deck deck;
    private PlayerNotifierAdapter notifier;


    public Match(List<Player> matchPlayers, Deck deck, PlayerNotifierAdapter notifier) {
        this.notifier = notifier;
        this.deck = deck;
        deck.shuffle();
        this.matchPlayers = matchPlayers;
        this.firstPlayerIndexInRound = 0;
    }

    private Match(List<Player> matchPlayers, int firstPlayerIndexInRound, Deck deck, PlayerNotifierAdapter notifier) {
        this.notifier = notifier;
        this.deck = deck;
        deck.shuffle();
        this.matchPlayers = matchPlayers;
        this.firstPlayerIndexInRound = firstPlayerIndexInRound;
    }

    public Match createNextMatch(){
        removePlayerWithNoChips();
        return new Match(matchPlayers, BetIterator.nextPlayerIndex(matchPlayers,firstPlayerIndexInRound), deck, notifier);
    }

    private void removePlayerWithNoChips() {
        List<Player> playerForNextRound = new ArrayList<>();
        matchPlayers.forEach(p -> {
            if(p.getChipsStack() > 0){
                playerForNextRound.add(p);
            }else{
                notifier.broadcastGameFinished();
            }
        });
        matchPlayers = playerForNextRound;
    }

    public void startMatch() {
        notifier.broadcastMatchStart(this);
        round = new Round(matchPlayers, firstPlayerIndexInRound, deck, notifier);
        round.startRound();
    }

    public void playerFold(Player player) {
        round.playerFold(player);
        ifRoundIsFinishedGoAhead();
    }

    public void playerCall(Player player) {
        round.playerCall(player);
        ifRoundIsFinishedGoAhead();
    }

    public void playerRaise(Player player, long raise) {
        round.playerRaise(player, raise);
        ifRoundIsFinishedGoAhead();
    }

    private void ifRoundIsFinishedGoAhead() {
        if(round.isFinished()){
            if(firstRound) {
                round = round.createSecondRound();
                round.startRound();
                firstRound = false;
            }else{
                showdown();
            }
        }
    }

    private void showdown() {
        Player winner = WiningStrategy.winner(matchPlayers, round.getBets());
        winner.setChipsStack(winner.getChipsStack()+round.getBets().getTotalPot());
        cleanHands();
        notifier.broadcastMatchFinished();
    }

    private void cleanHands() {
            matchPlayers.forEach(p -> p.cleanHand());
    }

    public boolean isFinished(){
        return round.isFinished() && firstRound == false;
    }

    public boolean hasMoreThanOnePlayerChips(){
        //never do this to shit!
        return matchPlayers.stream().mapToInt( p -> p.getChipsStack() > 0 ? 1 :0).sum() > 1;
    }

    public List<Player> getMatchPlayers() {
        return matchPlayers;
    }
}
