package hr.mlinx.core;

import hr.mlinx.core.data.CourtType;
import hr.mlinx.core.data.Player;
import hr.mlinx.core.initializer.DataInitializer;
import hr.mlinx.core.processor.CombinationProcessor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DataInitializer initializer = new DataInitializer();
            List<Player> backcourt = initializer.loadPlayers(CourtType.BACKCOURT);
            List<Player> frontcourt = initializer.loadPlayers(CourtType.FRONTCOURT);

            boolean withAdditional = backcourt.getFirst().hasAdditional();
            CombinationProcessor processor = new CombinationProcessor(withAdditional, DataInitializer.MOST_USED_PLAYER_KEY);
            processor.processAllCombinations(backcourt, frontcourt);

            System.out.println("Combinations written to " + processor.getCSVFilename());
        } catch (Exception e) {
            System.err.println(e.getClass().getSimpleName() + ": " + e.getLocalizedMessage());
            System.exit(1);
        }
    }
}
