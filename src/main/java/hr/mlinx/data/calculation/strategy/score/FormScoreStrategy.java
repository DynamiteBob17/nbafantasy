package hr.mlinx.data.calculation.strategy.score;

import hr.mlinx.data.Player;

public class FormScoreStrategy implements ScoreCalculationStrategy {
    private static final FormScoreStrategy INSTANCE = new FormScoreStrategy();

    private FormScoreStrategy() {
    }

    public static FormScoreStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public double calculateScore(Player player) {
        return player.getForm();
    }
}
