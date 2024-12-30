package hr.mlinx.core.data.calculation.strategy.combination;

import hr.mlinx.core.data.calculation.strategy.score.TotalPointsScoreStrategy;

public class BestCombinationByTotalPoints extends PlayerCombinationCalculator {
    public BestCombinationByTotalPoints() {
        scoreCalculationStrategy = TotalPointsScoreStrategy.getInstance();
    }
}
