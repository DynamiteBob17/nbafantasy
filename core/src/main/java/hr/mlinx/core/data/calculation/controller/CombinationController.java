package hr.mlinx.core.data.calculation.controller;

import hr.mlinx.core.data.Player;
import hr.mlinx.core.data.Team;
import hr.mlinx.core.data.calculation.strategy.combination.PlayerCombinationContext;
import hr.mlinx.core.data.calculation.strategy.combination.PlayerCombinationStrategy;

import java.util.List;

public class CombinationController {
    private final PlayerCombinationContext playerCombinationContext;

    public CombinationController(PlayerCombinationContext playerCombinationContext) {
        this.playerCombinationContext = playerCombinationContext;
    }

    public void changePlayerCombinationContextStrategy(PlayerCombinationStrategy strategy) {
        playerCombinationContext.setStrategy(strategy);
    }

    public Team findIdealCombination(
            List<Player> backcourtPlayers,
            List<Player> frontcourtPlayers,
            double backcourtSalary,
            double frontcourtSalary,
            int courtSize
    ) {
        List<Player> idealBackcourtPlayers = playerCombinationContext.executeStrategy(
                backcourtPlayers,
                backcourtSalary,
                courtSize
        );
        List<Player> idealFrontcourtPlayers = playerCombinationContext.executeStrategy(
                frontcourtPlayers,
                frontcourtSalary,
                courtSize
        );

        Team idealBackcourt = new Team(idealBackcourtPlayers);
        Team idealFrontcourt = new Team(idealFrontcourtPlayers);
        return Team.merge(
                idealBackcourt,
                idealFrontcourt,
                backcourtSalary,
                frontcourtSalary
        );
    }
}
