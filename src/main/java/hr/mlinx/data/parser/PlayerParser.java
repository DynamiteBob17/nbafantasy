package hr.mlinx.data.parser;

import hr.mlinx.data.CourtType;
import hr.mlinx.data.Player;
import hr.mlinx.exception.ParsingException;
import hr.mlinx.io.file.FileHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerParser {
    private PlayerParser() {
    }

    private static final String KEYS_MISMATCH_ERR_MSG = " (%s)".formatted("data keys mismatch".toUpperCase());

    @SuppressWarnings("unused")
    public static JSONObject getPlayersJsonFromFile(
            String filePath,
            String[] keys
    ) throws ParsingException, IOException {
        List<String> lines = FileHandler.readAllLines(filePath);
        return getPlayersJsonFromLines(lines, keys);
    }

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

            if (dataIndex > 2) {
                players.put(player);
                player = new JSONObject();
                dataIndex = 0;
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
                        if (line.matches(".*[a-zA-Z].*")) { // stats shouldn't contain any letter
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
        }

        json.put("players", players);
        return json;
    }

    public static List<Player> getPlayersFromJson(
            JSONObject json,
            String[] keys,
            boolean withAdditional,
            CourtType courtType
    ) throws ParsingException {
        try {
            JSONArray playersJson = json.getJSONArray("players");
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
            throw new ParsingException(e.getLocalizedMessage() + KEYS_MISMATCH_ERR_MSG);
        }
    }
}
