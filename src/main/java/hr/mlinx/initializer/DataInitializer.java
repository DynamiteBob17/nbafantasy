package hr.mlinx.initializer;

import hr.mlinx.data.CourtType;
import hr.mlinx.data.Player;
import hr.mlinx.data.parser.PlayerParser;
import hr.mlinx.exception.CookieDismissalException;
import hr.mlinx.exception.ParsingException;
import hr.mlinx.io.file.FileFormatType;
import hr.mlinx.io.file.FileHandler;
import hr.mlinx.io.input.UserInputHandler;
import hr.mlinx.web.PlayerStatsWebScraper;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class DataInitializer implements AutoCloseable {
    private final boolean willProvideJson;
    private final PlayerStatsWebScraper scraper;

    public static final String MOST_USED_PLAYER_KEY = "fantasyPpg";
    private static final String[] KEYS_ARRAY = new String[]{"name", "team", "salary", "form", "totalPoints", MOST_USED_PLAYER_KEY};

    public DataInitializer() {
        UserInputHandler userInputHandler = new UserInputHandler();
        String willProvideJsonInput = userInputHandler.takeInput(
                "Are you providing your own JSON files (%s and %s) (y/N)".formatted(
                        CourtType.BACKCOURT.getCourtName() + FileFormatType.JSON.getFileExtension(),
                        CourtType.FRONTCOURT.getCourtName() + FileFormatType.JSON.getFileExtension()
                ),
                "no"
        );
        this.willProvideJson = willProvideJsonInput.equalsIgnoreCase("y") ||
                willProvideJsonInput.equalsIgnoreCase("yes");

        this.scraper = willProvideJson ? null : new PlayerStatsWebScraper();
    }

    public List<Player> loadPlayers(CourtType courtType) throws ParsingException, IOException, CookieDismissalException, InterruptedException {
        String filenameJson = courtType.getCourtName() + FileFormatType.JSON.getFileExtension();

        if (willProvideJson) {
            String jsonContent = FileHandler.readFile(filenameJson);
            JSONObject courtJson = new JSONObject(jsonContent);
            return PlayerParser.getPlayersFromJson(courtJson, KEYS_ARRAY, true, courtType);
        } else {
            List<String> courtContents = scraper.scrapeContents(courtType);
            List<String> lines = String.join("\n", courtContents).lines().toList();
            JSONObject courtJson = PlayerParser.getPlayersJsonFromLines(lines, KEYS_ARRAY);

            FileHandler.writeFile(filenameJson, courtJson.toString(4));
            System.out.println("Players written to " + filenameJson);

            return PlayerParser.getPlayersFromJson(courtJson, KEYS_ARRAY, true, courtType);
        }
    }

    @Override
    public void close() {
        if (scraper != null) {
            scraper.quit();
        }
    }
}
