package hr.mlinx.processor;

import hr.mlinx.data.Player;
import hr.mlinx.data.Team;
import hr.mlinx.data.calculation.controller.CombinationController;
import hr.mlinx.data.calculation.manager.TopCombinationManager;
import hr.mlinx.data.calculation.manager.TopCombinationManagerByAdditional;
import hr.mlinx.data.calculation.manager.TopCombinationManagerByForm;
import hr.mlinx.data.calculation.manager.TopCombinationManagerByTotalPoints;
import hr.mlinx.data.calculation.strategy.combination.*;
import hr.mlinx.io.csv.CSVGenerator;
import hr.mlinx.io.file.FileFormatType;
import hr.mlinx.io.file.FileHandler;
import hr.mlinx.io.output.PleaseWaitOutput;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinationProcessor {
    private final Map<String, PlayerCombinationStrategy> strategies;
    private final String csvFilename;
    private final CombinationController combinationController;

    public CombinationProcessor(boolean withAdditional, String additionalFieldKey) {
        this.strategies = initializeStrategies(withAdditional, additionalFieldKey);
        this.csvFilename = generateCSVFilename();
        combinationController = new CombinationController(new PlayerCombinationContext(strategies.get("form")));
    }

    public String getCSVFilename() {
        return csvFilename;
    }

    public void processAllCombinations(List<Player> backcourt, List<Player> frontcourt) throws IOException {
        for (Map.Entry<String, PlayerCombinationStrategy> entry : strategies.entrySet()) {
            String scoreField = entry.getKey();
            PlayerCombinationStrategy strategy = entry.getValue();
            processCombinations(strategy, backcourt, frontcourt, scoreField);
        }
    }

    private void processCombinations(
            PlayerCombinationStrategy strategy,
            List<Player> backcourt,
            List<Player> frontcourt,
            String scoreField
    ) throws IOException {
        int courtSize = 5;
        TopCombinationManager combinationManager = getTopCombinationManager(strategy, courtSize);
        combinationController.changePlayerCombinationContextStrategy(strategy);

        int salaryGap = 10;
        double baseSalary = 50;

        PleaseWaitOutput.waitFor("calculating ideal combinations by", scoreField);

        for (int i = -salaryGap; i <= salaryGap; ++i) {
            combinationManager.addCombinationIfBetter(
                    combinationController.findIdealCombination(
                            backcourt, frontcourt, baseSalary + i, baseSalary- i, 5
                    )
            );
        }

        writeCombinationsToFile(scoreField, combinationManager.getTopCombinations());
    }

    private TopCombinationManager getTopCombinationManager(PlayerCombinationStrategy strategy, int courtSize) {
        return switch (strategy) {
            case BestCombinationByTotalPoints ignored ->
                    new TopCombinationManagerByTotalPoints(courtSize);
            case BestCombinationByAdditional ignored ->
                    new TopCombinationManagerByAdditional(courtSize);
            case null, default -> new TopCombinationManagerByForm(courtSize);
        };
    }

    private void writeCombinationsToFile(String scoreField, List<Team> topCombinations) throws IOException {
        CSVGenerator csvGenerator = CSVGenerator.getInstance();
        FileHandler.appendToFile(csvFilename, "TOP COMBINATIONS by " + scoreField.toUpperCase() + "\n");

        for (Team team : topCombinations) {
            FileHandler.appendToFile(csvFilename, csvGenerator.generateCompleteCSV(team) + "\n");
            String playersCSV = csvGenerator.generateCompleteCSV(team.getPlayers().getFirst(), team.getPlayers().toArray(new Player[0]));
            FileHandler.appendToFile(csvFilename, playersCSV);
        }

        FileHandler.appendToFile(csvFilename, "\n");
    }

    private Map<String, PlayerCombinationStrategy> initializeStrategies(boolean withAdditional, String additionalFieldKey) {
        Map<String, PlayerCombinationStrategy> strategyMap = new HashMap<>();
        strategyMap.put("form", new BestCombinationByForm());
        strategyMap.put("totalPoints", new BestCombinationByTotalPoints());
        if (withAdditional) {
            strategyMap.put(additionalFieldKey, new BestCombinationByAdditional());
        }
        return strategyMap;
    }

    private String generateCSVFilename() {
        return "combinations_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + FileFormatType.CSV.getFileExtension();
    }
}
