package hr.mlinx.core.data.calculation.strategy.combination;

import hr.mlinx.core.data.calculation.strategy.score.AdditionalScoreStrategy;

public class BestCombinationByAdditional extends PlayerCombinationCalculator {
    public BestCombinationByAdditional() {
        scoreCalculationStrategy = AdditionalScoreStrategy.getInstance();
    }
}
