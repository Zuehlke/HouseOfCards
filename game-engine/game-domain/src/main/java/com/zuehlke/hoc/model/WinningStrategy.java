package com.zuehlke.hoc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Calculates the winners of a match.
 */
public class WinningStrategy {

    private static int PAIR_SCORE_FACTOR = 20;


    public static List<Player> winners(List<Player> matchPlayers, Bets bets) {
        List<Player> remainingPlayersOfRound = filterFoldedPlayers(matchPlayers, bets);
        Map<Player, Integer> playerScores = calculatePlayerScores(remainingPlayersOfRound);
        return calculateWinners(playerScores);
    }

    /**
     * Removes players that folded during the match.
     * @param matchPlayers all players of the match
     * @param bets bets of the current round
     * @return active (non-folded) players of the match
     */
    private static List<Player> filterFoldedPlayers(List<Player> matchPlayers, Bets bets) {
        List<Player> remainingPlayers = new ArrayList<>();
        matchPlayers.forEach(player -> {
            if (!bets.playerHasFolded(player)) {
                remainingPlayers.add(player);
            }
        });
        return remainingPlayers;
    }


    private static Map<Player, Integer> calculatePlayerScores(List<Player> remainingPlayersOfRound) {
        Map<Player, Integer> playerScores = new HashMap<>();
        remainingPlayersOfRound.forEach(player -> {
            int score = 0;
            int firstCard = player.getFirstCard();
            int secondCard = player.getSecondCard();

            if (playerHasPair(player)) {
                score = PAIR_SCORE_FACTOR * NokerDeck.HIGHEST_CARD + firstCard;
            } else {
                score = NokerDeck.HIGHEST_CARD * Math.max(firstCard, secondCard)
                        + Math.min(firstCard, secondCard);
            }
            playerScores.put(player, score);
        });
        return playerScores;
    }


    private static List<Player> calculateWinners(Map<Player, Integer> playerScores) {
        List<Player> winners = new ArrayList<>();

        int highestScore = 0;
        for(Map.Entry<Player,Integer> playerScore : playerScores.entrySet()) {
            if (highestScore == 0 || playerScore.getValue() > highestScore) {
                highestScore = playerScore.getValue();
            }
        }

        int finalBestScore = highestScore;
        playerScores.entrySet().forEach(score -> {
            if (score.getValue() == finalBestScore) {
                winners.add(score.getKey());
            }
        });
        return winners;
    }


    private static boolean playerHasPair(Player player) {
        return player.getFirstCard() == player.getSecondCard();
    }
}
