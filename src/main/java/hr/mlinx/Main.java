package hr.mlinx;

import hr.mlinx.data.CourtType;
import hr.mlinx.data.Player;
import hr.mlinx.initializer.DataInitializer;
import hr.mlinx.processor.CombinationProcessor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (DataInitializer initializer = new DataInitializer()) {
            List<Player> backcourt = initializer.loadPlayers(CourtType.BACKCOURT);
            List<Player> frontcourt = initializer.loadPlayers(CourtType.FRONTCOURT);

            boolean withAdditional = backcourt.getFirst().hasAdditional();
            CombinationProcessor processor = new CombinationProcessor(withAdditional, DataInitializer.MOST_USED_PLAYER_KEY);
            processor.processAllCombinations(backcourt, frontcourt);

            System.out.println("Combinations written to " + processor.getCSVFilename());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Something went wrong");
        } catch (Exception e) {
            System.err.println(e.getClass().getSimpleName() + ": " + e.getLocalizedMessage());
        }
    }
}
