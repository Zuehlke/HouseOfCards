package com.zuehlke.hoc.model;

import com.zuehlke.hoc.PlayerNotifierAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents a match of a Noker game. A match consists
 * of two consecutive rounds of betting. Each time a new match is
 * started the player order is rotated by one and the deck is re-
 * shuffled.
 */
public class Match {

    private List<Player> matchPlayers;
    private Deck deck;
    private PlayerNotifierAdapter notifier;

    private int firstPlayerOfRoundIndex = 0;
    private Round round;


    public Match(List<Player> matchPlayers, Deck deck, PlayerNotifierAdapter notifier) {
        init(matchPlayers, deck, notifier);
    }

    private Match(List<Player> matchPlayers, int firstPlayerOfRoundIndex, Deck deck, PlayerNotifierAdapter notifier) {
        init(matchPlayers, deck, notifier);
        this.firstPlayerOfRoundIndex = firstPlayerOfRoundIndex;
    }

    private void init(List<Player> matchPlayers, Deck deck, PlayerNotifierAdapter notifier) {
        this.notifier = notifier;
        this.deck = deck;
        deck.shuffle();
        this.matchPlayers = matchPlayers;
    }

    /**
     * Creates and starts a new round.
     */
    public void startMatch() {
        notifier.broadcastMatchStart(this);
        round = new Round(matchPlayers, firstPlayerOfRoundIndex, deck, notifier);
        round.startRound();
    }

    /**
     * Creates a new match where the players that lost all of
     * their chips are excluded.
     * @return the next match of the game
     */
    public Match createNextMatch(){
        removePlayersWithNoChips();
        return new Match(matchPlayers, BetIterator.nextPlayerIndex(matchPlayers, firstPlayerOfRoundIndex), deck, notifier);
    }

    private void removePlayersWithNoChips() {
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


    public void playerFold(Player player) {
        round.playerFold(player);
        ifRoundIsFinishedGoAhead();
    }

    public void playerSet(Player player, long chips) {
        if (isRaiseMove(player, chips)) {
            round.playerRaise(player, chips);
        } else {
            round.playerCall(player);
        }
        ifRoundIsFinishedGoAhead();
    }

    private boolean isRaiseMove(Player player, long chips) {
        return chips > round.getBets().neededChipsToCall(player);
    }

    private void ifRoundIsFinishedGoAhead() {
        if (round.isFinished()){
            if (round.getCurrentRound() == Round.RoundNumber.FIRST_ROUND) {
                round = round.createSecondRound();
                round.startRound();
            } else {
                showdown();
            }
        }
    }

    private void showdown() {
        List<Player> winners = WinningStrategy.winners(matchPlayers, round.getBets());
        long wonChipsShare = round.getBets().getTotalPot() / winners.size();

        winners.forEach(winner -> winner.setChipsStack(winner.getChipsStack() + wonChipsShare));

        cleanHands();
        notifier.broadcastMatchFinished(winners);
    }

    private void cleanHands() {
            matchPlayers.forEach(Player::cleanHand);
    }

    public boolean isFinished(){
        return round.isFinished() && round.getCurrentRound() == Round.RoundNumber.SECOND_ROUND;
    }

    public boolean hasMoreThanOnePlayerChips(){
        //never do this to shit!
        return matchPlayers.stream().mapToInt( p -> p.getChipsStack() > 0 ? 1 :0).sum() > 1;
    }

    public List<Player> getMatchPlayers() {
        return matchPlayers;
    }

    public Player getFirstPlayerInTurn() {
        return matchPlayers.get(firstPlayerOfRoundIndex);
    }
}
