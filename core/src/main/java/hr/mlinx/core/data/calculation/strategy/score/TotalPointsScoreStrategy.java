package hr.mlinx.core.data.calculation.strategy.score;

import hr.mlinx.core.data.Player;

public class TotalPointsScoreStrategy implements ScoreCalculationStrategy {
    private static final TotalPointsScoreStrategy INSTANCE = new TotalPointsScoreStrategy();

    private TotalPointsScoreStrategy() {
    }

    public static TotalPointsScoreStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public double calculateScore(Player player) {
        return player.getTp();
    }
}
