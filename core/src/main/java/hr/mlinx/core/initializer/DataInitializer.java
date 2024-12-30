package hr.mlinx.core.initializer;

import hr.mlinx.core.data.CourtType;
import hr.mlinx.core.data.Player;
import hr.mlinx.core.data.json.JsonPlayerExtractor;
import hr.mlinx.core.exception.HttpRequestFailedException;
import hr.mlinx.core.exception.JsonPlayerExtractionException;
import hr.mlinx.core.io.file.FileFormatType;
import hr.mlinx.core.io.file.FileHandler;
import hr.mlinx.core.io.input.UserInputHandler;
import hr.mlinx.core.io.output.PleaseWaitOutput;
import hr.mlinx.core.request.ApiClient;
import hr.mlinx.core.request.QueryParam;
import hr.mlinx.core.request.factory.ApiClientFactory;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class DataInitializer {
    private final boolean willProvideJson;
    private final ApiClient apiClient;

    public static final String MOST_USED_PLAYER_KEY = "fantasyPpg";
    private static final String[] KEYS_ARRAY = new String[]{"name", "team", "salary", "form", "totalPoints", MOST_USED_PLAYER_KEY};

    public DataInitializer() throws IOException {
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
        this.apiClient = ApiClientFactory.createApiClientFromProperties();
    }

    public List<Player> loadPlayers(CourtType courtType) throws IOException, JsonPlayerExtractionException, HttpRequestFailedException {
        String filenameJson = courtType.getCourtName() + FileFormatType.JSON.getFileExtension();
        JSONObject courtJson;

        if (willProvideJson) {
            PleaseWaitOutput.waitFor("reading file", filenameJson);
            String jsonContent = FileHandler.readFile(filenameJson);
            courtJson = new JSONObject(jsonContent);
        } else {
            PleaseWaitOutput.waitFor("fetching stats for", courtType.getCourtName());
            courtJson = apiClient.get("/api/scraper/stats", new QueryParam<>("courtType", courtType));
            FileHandler.writeFile(filenameJson, courtJson.toString(4));
            System.out.println("Players written to " + filenameJson);
        }

        return JsonPlayerExtractor.getPlayersFromJson(courtJson, KEYS_ARRAY, true, courtType);
    }
}
