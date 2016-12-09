package com.zuehlke.hoc.model;

import com.zuehlke.hoc.NokerSettings;

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

    private long maximumToSet = NokerSettings.INITIAL_CHIPS - NokerSettings.BLIND;

    public long getTotalPot(){
        return bets.values().stream().mapToLong(l -> l).sum();
    }

    public void startNextBetRound(){
        calledPlayers.clear();
    }

    public void playerFolds(Player player) {
        foldedPlayers.add(player);
        calledPlayers.remove(player);
        activePlayers.remove(player);
    }

    public long playerCalls(Player player){
        if (!playerHasFolded(player)) {
            requiemsPlayerHasEnoughChipsToCall(player);
            long neededChipsToCall = neededChipsToCall(player);
            player.setChipsStack(player.getChipsStack() - neededChipsToCall);
            bets.put(player, bets.get(player) + neededChipsToCall);
            calledPlayers.add(player);
            return neededChipsToCall;
        }
        return 0;
    }

    private void requiemsPlayerHasEnoughChipsToCall(Player player) {
        if(player.getChipsStack() < neededChipsToCall(player)){
            throw new RuntimeException("Player has to less chips!");
        }
    }

    public long neededChipsToCall(Player player) {
        return highestBet - (getPlayersBet(player) - NokerSettings.BLIND);
    }

    public void withdrawBlindFromPlayer(Player player) {
        player.decreaseChipsStack(NokerSettings.BLIND);
        bets.put(player, NokerSettings.BLIND);
        activePlayers.add(player);
    }

    public void playerRaise(Player player, long raiseAmount){
        if (!playerHasFolded(player)) {
            if (isValidRaiseAmount(player, raiseAmount)) {
                long raisedChips = neededChipsToRaise(player, raiseAmount);
                player.setChipsStack(player.getChipsStack() - raisedChips);
                bets.put(player, bets.get(player) + raiseAmount);
                highestBet = raisedChips;
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

    private long minChipStackOfActivePlayers() {
        long minChipStack = NokerSettings.INITIAL_CHIPS - NokerSettings.BLIND;
        for (Player p : activePlayers) {
            long playerChipsStack = p.getChipsStack();
            if (playerChipsStack < minChipStack) {
                minChipStack = playerChipsStack;
            }
        }
        return minChipStack;
    }

    private boolean isValidRaiseAmount(Player player, long raise) {
        return raise <= maximumToSet && playerHasEnoughChipsToRaise(player, raise);
    }

    private boolean playerHasEnoughChipsToRaise(Player player, long raise) {
        return raise <= player.getChipsStack();
    }

    private long neededChipsToRaise(Player player, long raise) {
        return (getPlayersBet(player) - NokerSettings.BLIND) + raise;
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

    public Set<Player> getActivePlayers() {
        return activePlayers;
    }

    public Set<Player> getFoldedPlayers() {
        return foldedPlayers;
    }

    public long getMaximumToSet() {
        return minChipStackOfActivePlayers();
    }

    public long getHighestBet() {
        return highestBet;
    }
}
