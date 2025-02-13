package hr.mlinx.core.data.calculation.strategy.combination;

import hr.mlinx.core.data.Player;

import java.util.ArrayList;
import java.util.List;

public class TransactionOptimizer extends PlayerCombinationCalculator {
    public List<Player> suggestTransactions(
            List<Player> currentTeam,
            List<Player> availablePlayers,
            double salaryLimit
    ) {
        double currentTeamScore = calculateTeamScore(currentTeam);
        List<TransactionDetail> suggestedTransactions = new ArrayList<>();

        List<Player> sortedAvailablePlayers = new ArrayList<>(availablePlayers);
        sortedAvailablePlayers.sort((p1, p2) ->
                Double.compare(p2.calculateScore(scoreCalculationStrategy) / p2.getSalary(),
                        p1.calculateScore(scoreCalculationStrategy) / p1.getSalary()));

        for (int i = 0; i < currentTeam.size(); i++) {
            Player currentPlayer = currentTeam.get(i);

            for (Player candidate : sortedAvailablePlayers) {
                if (candidate.getSalary() + calculateTeamSalary(currentTeam) - currentPlayer.getSalary() <= salaryLimit &&
                        candidate.calculateScore(scoreCalculationStrategy) > currentPlayer.calculateScore(scoreCalculationStrategy)) {

                    suggestedTransactions.add(new TransactionDetail(currentPlayer, candidate));
                    currentTeam.set(i, candidate);
                    break;
                }
            }
        }

        // Apply penalty logic for transactions
        int penalty = Math.max(0, suggestedTransactions.size() - 2) * 100; // 2 free transactions
        double finalScore = currentTeamScore - penalty;

        System.out.println("Suggested Transactions:");
        suggestedTransactions.forEach(System.out::println);
        System.out.println("Final Score after Penalty: " + finalScore);
        return currentTeam;
    }

    private double calculateTeamScore(List<Player> currentTeam) {
        return currentTeam.stream().mapToDouble(p -> p.calculateScore(scoreCalculationStrategy)).sum();
    }

    private double calculateTeamSalary(List<Player> team) {
        return team.stream().mapToDouble(Player::getSalary).sum();
    }

    record TransactionDetail(Player oldPlayer, Player newPlayer) {
        @Override
        public String toString() {
            return "Swap: " + oldPlayer + " -> " + newPlayer;
        }
    }
}
