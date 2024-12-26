package hr.mlinx.data.calculation.strategy.combination;

import hr.mlinx.data.calculation.strategy.score.TotalPointsScoreStrategy;

public class BestCombinationByTotalPoints extends PlayerCombinationCalculator {
    public BestCombinationByTotalPoints() {
        scoreCalculationStrategy = TotalPointsScoreStrategy.getInstance();
    }
}
