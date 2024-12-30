package hr.mlinx.core.data.calculation.strategy.combination;

import hr.mlinx.core.data.calculation.strategy.score.FormScoreStrategy;

public class BestCombinationByForm extends PlayerCombinationCalculator {
    public BestCombinationByForm() {
        scoreCalculationStrategy = FormScoreStrategy.getInstance();
    }
}
