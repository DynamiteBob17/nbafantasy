package hr.mlinx.core.data.calculation.strategy.combination;

import hr.mlinx.core.data.Player;

import java.util.List;

public interface PlayerCombinationStrategy {
    List<Player> generateIdealCombination(
            List<Player> players,
            double salaryLimit,
            int teamSize
    );
}
