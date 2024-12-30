package hr.mlinx.core.data.calculation.strategy.score;

import hr.mlinx.core.data.Player;

public interface ScoreCalculationStrategy {
    double calculateScore(Player player);
}
