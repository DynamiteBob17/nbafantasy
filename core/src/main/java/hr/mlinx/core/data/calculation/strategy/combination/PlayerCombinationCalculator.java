package hr.mlinx.core.data.calculation.strategy.combination;

import hr.mlinx.core.data.Player;
import hr.mlinx.core.data.calculation.strategy.score.ScoreCalculationStrategy;

import java.util.ArrayList;
import java.util.List;

public abstract class PlayerCombinationCalculator implements PlayerCombinationStrategy {
    protected ScoreCalculationStrategy scoreCalculationStrategy;

    @Override
    public List<Player> generateIdealCombination(
            List<Player> players,
            double salaryLimit,
            int teamSize
    ) {
        return dpKnapsack(
                players,
                salaryLimit,
                teamSize,
                scoreCalculationStrategy
        );
    }

    private List<Player> dpKnapsack(
            List<Player> players,
            double salaryLimit,
            int teamSize,
            ScoreCalculationStrategy strategy
    ) {
        int n = players.size();

        // convert salary to integer for easier DP indexing
        int granularityIncrease = 100;
        int salaryLimitInt = (int) Math.ceil(salaryLimit * granularityIncrease);

        // DP table: dp[i][j][k] - maximum score using first i players, budget j, selecting exactly k players
        double[][][] dp = new double[n + 1][salaryLimitInt + 1][teamSize + 1];

        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= salaryLimitInt; ++j) {
                dp[i][j][0] = 0; // score is 0 when selecting 0 players
            }
        }

        // Fill DP table with bias for better salary utilization
        for (int i = 1; i <= n; ++i) {
            int salary = (int) Math.ceil(players.get(i - 1).getSalary() * granularityIncrease);
            double score = players.get(i - 1).calculateScore(strategy);

            for (int j = 0; j <= salaryLimitInt; ++j) {
                for (int k = 1; k <= teamSize; ++k) {
                    // Option 1: exclude player i-1
                    dp[i][j][k] = dp[i - 1][j][k];

                    // Option 2: include player i-1 if budget allows
                    if (j >= salary) {
                        // Apply bias for better salary utilization
                        double bias = 1e-6 * (salaryLimitInt - (j - salary));
                        dp[i][j][k] = Math.max(dp[i][j][k], dp[i - 1][j - salary][k - 1] + score - bias);
                    }
                }
            }
        }

        // Reconstruct the solution
        List<Player> selectedPlayers = new ArrayList<>();
        int j = salaryLimitInt;
        int k = teamSize;
        for (int i = n; i > 0 && k > 0; --i) {
            int salary = (int) Math.ceil(players.get(i - 1).getSalary() * granularityIncrease);

            // Check if player i-1 was included
            if (j >= salary && dp[i][j][k] == dp[i - 1][j - salary][k - 1] + players.get(i - 1).calculateScore(strategy) - 1e-6 * (salaryLimitInt - (j - salary))) {
                selectedPlayers.add(players.get(i - 1));
                j -= salary;
                --k;
            }
        }

        return selectedPlayers;
    }
}
