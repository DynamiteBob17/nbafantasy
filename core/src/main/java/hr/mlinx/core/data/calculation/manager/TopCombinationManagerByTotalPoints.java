package hr.mlinx.core.data.calculation.manager;

import hr.mlinx.core.data.calculation.strategy.score.TotalPointsScoreStrategy;

public class TopCombinationManagerByTotalPoints extends TopCombinationManager {
    public TopCombinationManagerByTotalPoints(int maxCombinations) {
        super(maxCombinations);
        this.scoreCalculationStrategy = TotalPointsScoreStrategy.getInstance();
    }
}
