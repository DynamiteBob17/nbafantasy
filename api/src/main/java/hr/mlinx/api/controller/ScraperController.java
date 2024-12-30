package hr.mlinx.api.controller;

import hr.mlinx.api.data.CourtType;
import hr.mlinx.api.exception.CookieDismissalException;
import hr.mlinx.api.exception.ParsingException;
import hr.mlinx.api.service.PlayerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scraper")
public class ScraperController {
    private final PlayerService playerService;

    public ScraperController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPlayerStats(@RequestParam CourtType courtType) throws ParsingException, CookieDismissalException, InterruptedException {
        return ResponseEntity.ok(playerService.getPlayers(courtType).toString());
    }
}
