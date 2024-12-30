package hr.mlinx.core.data.calculation.manager;

import hr.mlinx.core.data.calculation.strategy.score.FormScoreStrategy;

public class TopCombinationManagerByForm extends TopCombinationManager {
    public TopCombinationManagerByForm(int maxCombinations) {
        super(maxCombinations);
        this.scoreCalculationStrategy = FormScoreStrategy.getInstance();
    }
}
