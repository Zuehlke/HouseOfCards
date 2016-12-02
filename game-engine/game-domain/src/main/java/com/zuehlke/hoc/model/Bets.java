package com.zuehlke.hoc.model;

import com.zuehlke.hoc.NokerGame;

import java.util.*;

/**
 * The {@link Bets} class provides the functionality to hold the status of the bets
 * of the current match. It can be used in all rounds by resetting the bet round with
 * the {@link #startNextBetRound()} method.
 */
public class Bets {

    private final Map<Player, Long> bets = new HashMap<>();
    private final Set<Player> foldedPlayers = new HashSet<>();
    private final Set<Player> calledPlayers = new HashSet<>();
    private final Set<Player> activePlayers = new HashSet<>();
    private long highestBet = 0;
    private long maxRaiseAmount = NokerGame.INITIAL_CHIPS;


    public long getTotalPot(){
        return bets.values().stream().mapToLong(l -> l).sum();
    }

    public void startNextBetRound(){
        calledPlayers.clear();
    }

    public void playerFolds(Player player) {
        foldedPlayers.add(player);
        calledPlayers.remove(player);
    }

    public void playerCalls(Player player){
        if (!playerHasFolded(player)) {
            requiemsPlayerHasEnoughChipsToCall(player);
            player.setChipsStack(player.getChipsStack() - neededChipsToCall(player));
            bets.put(player, highestBet);
            calledPlayers.add(player);
        }
    }

    private void requiemsPlayerHasEnoughChipsToCall(Player player) {
        if(player.getChipsStack() < neededChipsToCall(player)){
            throw new RuntimeException("Player has to less chips!");
        }
    }

    public long neededChipsToCall(Player player) {
        return highestBet - getPlayersBet(player);
    }


    public void playerRaise(Player player, long raiseAmount){
        if (!playerHasFolded(player)) {
            if (isValidRaiseAmount(player, raiseAmount)) {
                player.setChipsStack(player.getChipsStack() - neededChipsToRaise(player, raiseAmount));
                bets.put(player, highestBet + raiseAmount);
                highestBet += raiseAmount;
                maxRaiseAmount = minChipStackOfActivePlayers(player);
                calledPlayers.clear();
                calledPlayers.add(player);
            } else {
                // TODO:
                // Currently if a player places an invalid raise amount
                // the move is automatically overwritten by a fold move, assuming
                // that if the player is a bot the bot was not programmed correctly and
                // the probability is high that future moves will also be incorrect.
                // As this is not directly a concern of the game domain this should be
                // handled differently e.g. by notifying the player about the incorrect
                // raise move.
                playerFolds(player);
                // TODO: notify the player that he folded
            }
        }
    }

    private long minChipStackOfActivePlayers(Player currentPlayer) {
        activePlayers.clear();
        calledPlayers.forEach(activePlayers::add);
        activePlayers.add(currentPlayer);

        long minChipStack = NokerGame.INITIAL_CHIPS;
        for (Player p : activePlayers) {
            long playerChipsStack = p.getChipsStack();
            if (playerChipsStack < minChipStack) {
                minChipStack = playerChipsStack;
            }
        }
        return minChipStack;
    }


    private boolean playerHasEnoughChipsToRaise(Player player, long raise) {
        return player.getChipsStack() > neededChipsToRaise(player, raise);
    }


    private boolean isValidRaiseAmount(Player player, long raise) {
        return raise <= maxRaiseAmount && playerHasEnoughChipsToRaise(player, raise);
    }


    private long neededChipsToRaise(Player player, long raise) {
        return (highestBet - getPlayersBet(player)) + raise;
    }


    private long getPlayersBet(Player player){
        return bets.containsKey(player) ? bets.get(player) : 0;
    }

    public boolean playerHasFolded(Player player) {
        return foldedPlayers.contains(player);
    }

    public boolean playerHasCalled(Player player) {
        return calledPlayers.contains(player);
    }

}
