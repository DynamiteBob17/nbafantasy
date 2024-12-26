package hr.mlinx.data.calculation.strategy.score;

import hr.mlinx.data.Player;

public interface ScoreCalculationStrategy {
    double calculateScore(Player player);
}
