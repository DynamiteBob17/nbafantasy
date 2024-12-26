package hr.mlinx.data.calculation.strategy.combination;

import hr.mlinx.data.Player;

import java.util.List;

public interface PlayerCombinationStrategy {
    List<Player> generateIdealCombination(
            List<Player> players,
            double salaryLimit,
            int teamSize
    );
}
