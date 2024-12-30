package hr.mlinx.api.service;

import hr.mlinx.api.cache.service.CachingScraperService;
import hr.mlinx.api.data.CourtType;
import hr.mlinx.api.data.parser.StatsParser;
import hr.mlinx.api.exception.CookieDismissalException;
import hr.mlinx.api.exception.ParsingException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final CachingScraperService cachingScraperService;

    public PlayerServiceImpl(CachingScraperService cachingScraperService) {
        this.cachingScraperService = cachingScraperService;
    }

    @Override
    public JSONObject getPlayers(CourtType courtType) throws ParsingException, CookieDismissalException, InterruptedException {
        List<String> courtContents = cachingScraperService.scrapeContents(courtType);
        List<String> lines = String.join("\n", courtContents).lines().toList();
        return StatsParser.getPlayersJsonFromLines(lines, new String[]{"name", "team", "salary", "form", "totalPoints", "fantasyPpg"});
    }
}
