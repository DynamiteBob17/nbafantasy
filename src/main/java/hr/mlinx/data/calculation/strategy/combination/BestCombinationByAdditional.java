package hr.mlinx.data.calculation.strategy.combination;

import hr.mlinx.data.calculation.strategy.score.AdditionalScoreStrategy;

public class BestCombinationByAdditional extends PlayerCombinationCalculator {
    public BestCombinationByAdditional() {
        scoreCalculationStrategy = AdditionalScoreStrategy.getInstance();
    }
}
