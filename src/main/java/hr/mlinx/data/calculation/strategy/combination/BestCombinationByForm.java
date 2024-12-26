package hr.mlinx.data.calculation.strategy.combination;

import hr.mlinx.data.calculation.strategy.score.FormScoreStrategy;

public class BestCombinationByForm extends PlayerCombinationCalculator {
    public BestCombinationByForm() {
        scoreCalculationStrategy = FormScoreStrategy.getInstance();
    }
}
