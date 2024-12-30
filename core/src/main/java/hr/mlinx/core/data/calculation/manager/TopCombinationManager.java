package hr.mlinx.core.data.calculation.manager;


import hr.mlinx.core.data.Team;
import hr.mlinx.core.data.calculation.strategy.score.ScoreCalculationStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class TopCombinationManager {
    private final int maxCombinations;
    private final List<Team> topCombinations;
    protected ScoreCalculationStrategy scoreCalculationStrategy;

    protected TopCombinationManager(int maxCombinations) {
        this.maxCombinations = maxCombinations;
        this.topCombinations = new ArrayList<>();
    }

    public void addCombinationIfBetter(Team team) {
        topCombinations.add(team);
        topCombinations.sort(Comparator.comparingDouble(t -> ((Team) t).calculateScore(scoreCalculationStrategy)).reversed());
        if (topCombinations.size() > maxCombinations) {
            topCombinations.removeLast();
        }
        for (int i = 0; i < topCombinations.size(); ++i) {
            topCombinations.get(i).setName("TEAM #" + (i + 1));
        }
    }

    public List<Team> getTopCombinations() {
        return Collections.unmodifiableList(topCombinations);
    }
}
