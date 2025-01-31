package hr.mlinx.api.data.parser;

import hr.mlinx.api.exception.ParsingException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class StatsParser {
    private StatsParser() {
    }

    private static final String KEYS_MISMATCH_ERR_MSG = " (%s)".formatted("data keys mismatch".toUpperCase());

    public static JSONObject getPlayersJsonFromLines(
            List<String> lines,
            String[] keys
    ) throws ParsingException {
        JSONObject json = new JSONObject();
        JSONArray players = new JSONArray();
        JSONObject player = new JSONObject();

        int nonNumericalKeysAmount = 2;
        String[] numericalKeys = new String[keys.length - nonNumericalKeysAmount];
        System.arraycopy(keys, nonNumericalKeysAmount, numericalKeys, 0, numericalKeys.length);

        int dataIndex = 0;
        for (String line : lines) {
            line = line.trim();

            if (line.isBlank() ||
                    line.matches("[i!]") ||
                    line.startsWith("Player")) {
                continue;
            }

            switch (dataIndex) {
                case 0 -> player.put(keys[0], line);
                case 1 -> player.put(keys[1], line);
                case 2 -> {
                    String[] parts = line.split("[ \t]");

                    try {
                        player.put(numericalKeys[0], Double.parseDouble(parts[0]));

                        // ignore the 2nd column as we don't care about Sel. %
                        for (int i = 2; i < parts.length; ++i) {
                            player.put(numericalKeys[i - 1], Double.parseDouble(parts[i]));
                        }
                    } catch (Exception e) {
                        // there's a specific player with no name that breaks everything,
                        // but has a team, so his team and stats are picked up as case 0 and 1,
                        // and then the name of the following player is picked up in case 2
                        // which we check for here
                        if (line.matches(".*[a-zA-Z].*")) { // stats shouldn't contain letters
                            player = new JSONObject();
                            player.put(keys[0], line);
                            dataIndex = 1;
                            continue;
                        }

                        throw new ParsingException(e.getLocalizedMessage() + KEYS_MISMATCH_ERR_MSG);
                    }
                }
                default -> throw new ParsingException("Something went wrong");
            }

            ++dataIndex;

            if (dataIndex > 2) {
                players.put(player);
                player = new JSONObject();
                dataIndex = 0;
            }
        }

        json.put("players", players);
        return json;
    }
}
