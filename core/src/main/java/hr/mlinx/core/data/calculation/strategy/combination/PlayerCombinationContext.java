package hr.mlinx.core.data.calculation.strategy.combination;

import hr.mlinx.core.data.Player;

import java.util.List;

public class PlayerCombinationContext {
    private PlayerCombinationStrategy strategy;

    public PlayerCombinationContext(PlayerCombinationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PlayerCombinationStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Player> executeStrategy(
            List<Player> players,
            double salaryLimit,
            int teamSize
    ) {
        if (strategy == null) {
            throw new IllegalStateException("Strategy must be set before execution.");
        }

        return strategy.generateIdealCombination(
                players,
                salaryLimit,
                teamSize
        );
    }
}
