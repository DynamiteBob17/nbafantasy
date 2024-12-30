package hr.mlinx.core;

import hr.mlinx.core.data.CourtType;
import hr.mlinx.core.data.Player;
import hr.mlinx.core.initializer.DataInitializer;
import hr.mlinx.core.processor.CombinationProcessor;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("MAIN");
/*        try (DataInitializer initializer = new DataInitializer()) {
            List<Player> backcourt = initializer.loadPlayers(CourtType.BACKCOURT);
            List<Player> frontcourt = initializer.loadPlayers(CourtType.FRONTCOURT);

            boolean withAdditional = backcourt.getFirst().hasAdditional();
            CombinationProcessor processor = new CombinationProcessor(withAdditional, DataInitializer.MOST_USED_PLAYER_KEY);
            processor.processAllCombinations(backcourt, frontcourt);

            System.out.println("Combinations written to " + processor.getCSVFilename());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Something went wrong");
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getClass().getSimpleName() + ": " + e.getLocalizedMessage());
            System.exit(1);
        }

        System.exit(0);

        // the System.exit uses are for the exec:java goal to shut down the JVM and clean up,
        // since exec:java gives a warning that some threads didn't finish despite being asked to
        // via interruption after waiting for ~15s*/
    }
}
