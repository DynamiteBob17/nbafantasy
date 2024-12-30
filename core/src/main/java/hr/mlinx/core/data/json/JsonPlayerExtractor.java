package hr.mlinx.core.data.json;

import hr.mlinx.core.data.CourtType;
import hr.mlinx.core.data.Player;
import hr.mlinx.core.exception.JsonPlayerExtractionException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonPlayerExtractor {
    private JsonPlayerExtractor() {
    }

    private static final String KEYS_MISMATCH_ERR_MSG = " (%s)".formatted("data keys mismatch".toUpperCase());

    public static List<Player> getPlayersFromJson(
            JSONObject courtJson,
            String[] keys,
            boolean withAdditional,
            CourtType courtType
    ) throws JsonPlayerExtractionException {
        try {
            JSONArray playersJson = courtJson.getJSONArray("players");
            List<Player> players = new ArrayList<>(playersJson.length());

            for (int i = 0; i < playersJson.length(); ++i) {
                JSONObject playerJson = playersJson.getJSONObject(i);
                Player.Builder builder = new Player.Builder();

                builder.name(playerJson.getString(keys[0]))
                        .team(playerJson.getString(keys[1]))
                        .salary(playerJson.getDouble(keys[2]))
                        .form(playerJson.getDouble(keys[3]))
                        .tp(playerJson.getDouble(keys[4]))
                        .courtType(courtType);

                if (withAdditional) {
                    builder.withAdditional(keys[5], playerJson.getDouble(keys[5]));
                }

                players.add(builder.build());
            }

            return players;
        } catch (Exception e) {
            throw new JsonPlayerExtractionException(e.getLocalizedMessage() + KEYS_MISMATCH_ERR_MSG);
        }
    }
}
