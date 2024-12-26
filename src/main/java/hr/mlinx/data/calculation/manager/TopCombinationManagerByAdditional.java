package hr.mlinx.data.calculation.manager;

import hr.mlinx.data.calculation.strategy.score.AdditionalScoreStrategy;

public class TopCombinationManagerByAdditional extends TopCombinationManager {
    public TopCombinationManagerByAdditional(int maxCombinations) {
        super(maxCombinations);
        this.scoreCalculationStrategy = AdditionalScoreStrategy.getInstance();
    }
}
