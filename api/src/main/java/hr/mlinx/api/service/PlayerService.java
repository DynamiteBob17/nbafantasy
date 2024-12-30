package hr.mlinx.api.service;

import hr.mlinx.api.data.CourtType;
import hr.mlinx.api.exception.CookieDismissalException;
import hr.mlinx.api.exception.ParsingException;
import org.json.JSONObject;

public interface PlayerService {
    JSONObject getPlayers(CourtType courtType) throws ParsingException, CookieDismissalException, InterruptedException;
}
