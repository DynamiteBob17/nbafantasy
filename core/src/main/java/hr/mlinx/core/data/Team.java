package hr.mlinx.core.data;

import hr.mlinx.core.data.calculation.strategy.score.ScoreCalculationStrategy;
import hr.mlinx.core.data.calculation.visitor.SumVisitor;
import hr.mlinx.core.io.csv.CSVLineGenerator;
import hr.mlinx.core.io.csv.CSVPrintable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Team implements CSVPrintable {
    private String name;
    private final List<Player> players;
    private final double backcourtSalary;
    private final double frontcourtSalary;

    public Team(String name, List<Player> players, double backcourtSalary, double frontcourtSalary) {
        this.name = name;
        this.players = players;
        this.backcourtSalary = backcourtSalary;
        this.frontcourtSalary = frontcourtSalary;
    }

    public Team(String name, List<Player> players) {
        this(name, players, -1, -1);
    }

    public Team(List<Player> players, double backcourtSalary, double frontcourtSalary) {
        this("", players, backcourtSalary, frontcourtSalary);
    }

    public Team(List<Player> players) {
        this("", players);
    }

    public static Team merge(Team team1, Team team2, double backcourtSalary, double frontcourtSalary) {
        List<Player> newPlayers = team1.getPlayers();
        newPlayers.addAll(team2.getPlayers());
        return new Team(newPlayers, backcourtSalary, frontcourtSalary);
    }

    @SuppressWarnings("unused")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public double calculateSum(Function<Player, Double> fieldExtractor) {
        SumVisitor visitor = new SumVisitor(fieldExtractor);
        players.forEach(p -> p.accept(visitor));
        return visitor.getSum();
    }

    public double calculateScore(ScoreCalculationStrategy strategy) {
        return players.stream()
                .mapToDouble(strategy::calculateScore)
                .sum();
    }

    private static Player findPlayerWithAdditional(Team team) {
        return team.getPlayers().stream()
                .filter(Player::hasAdditional)
                .findFirst()
                .orElse(null);
    }

    @Override
    public String getCSVHeader(CSVLineGenerator csvLineGenerator) {
        List<String> header = new ArrayList<>(List.of("name", "totalSalary", "totalForm", "totalTotalPoints"));

        Player playerWithAdditional = findPlayerWithAdditional(this);
        if (playerWithAdditional != null) {
            char additionalFirstChar = playerWithAdditional.getAdditionalName().charAt(0);
            header.add("total" +
                    Character.toUpperCase(additionalFirstChar)
                    + playerWithAdditional.getAdditionalName().substring(1)
            );
        }

        header.add("backcourtSalary");
        header.add("frontcourtSalary");

        return csvLineGenerator.generateStringValues(header.toArray(new String[0]));
    }

    @Override
    public String getCSVValues(CSVLineGenerator csvLineGenerator) {
        List<Double> values = new ArrayList<>(List.of(
                calculateSum(Player::getSalary),
                calculateSum(Player::getForm),
                calculateSum(Player::getTp)
        ));

        Player playerWithAdditional = findPlayerWithAdditional(this);
        if (playerWithAdditional != null) {
            values.add(calculateSum(Player::getAdditionalValue));
        }

        values.add(backcourtSalary);
        values.add(frontcourtSalary);

        return csvLineGenerator.generateStringValues(
                name, csvLineGenerator.generateDoubleValues(values.toArray(new Double[0]))
        );
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                '}';
    }
}
