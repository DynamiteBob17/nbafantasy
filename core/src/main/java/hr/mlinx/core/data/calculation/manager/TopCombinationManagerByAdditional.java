package hr.mlinx.core.data.calculation.manager;

import hr.mlinx.core.data.calculation.strategy.score.AdditionalScoreStrategy;

public class TopCombinationManagerByAdditional extends TopCombinationManager {
    public TopCombinationManagerByAdditional(int maxCombinations) {
        super(maxCombinations);
        this.scoreCalculationStrategy = AdditionalScoreStrategy.getInstance();
    }
}
