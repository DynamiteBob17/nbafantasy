package hr.mlinx.core.data.calculation.strategy.score;

import hr.mlinx.core.data.Player;

public class AdditionalScoreStrategy implements ScoreCalculationStrategy {
    private static final AdditionalScoreStrategy INSTANCE = new AdditionalScoreStrategy();

    private AdditionalScoreStrategy() {
    }

    public static AdditionalScoreStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public double calculateScore(Player player) {
        return player.getAdditionalValue();
    }
}
