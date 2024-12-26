package hr.mlinx.data.calculation.manager;

import hr.mlinx.data.calculation.strategy.score.FormScoreStrategy;

public class TopCombinationManagerByForm extends TopCombinationManager {
    public TopCombinationManagerByForm(int maxCombinations) {
        super(maxCombinations);
        this.scoreCalculationStrategy = FormScoreStrategy.getInstance();
    }
}
