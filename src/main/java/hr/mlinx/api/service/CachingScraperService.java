package hr.mlinx.api.service;

import hr.mlinx.api.data.CourtType;
import hr.mlinx.api.exception.CookieDismissalException;
import hr.mlinx.api.scraper.PlayerStatsWebScraper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CachingScraperService {
    private final ThreadLocal<PlayerStatsWebScraper> scraperThreadLocal = ThreadLocal.withInitial(PlayerStatsWebScraper::new);

    @Cacheable(value = "playerStats", key = "#courtType")
    public List<String> scrapeContents(CourtType courtType) throws CookieDismissalException, InterruptedException {
        PlayerStatsWebScraper scraper = scraperThreadLocal.get();
        return scraper.scrapeContents(courtType);
    }

    public void cleanup() {
        PlayerStatsWebScraper scraper = scraperThreadLocal.get();
        scraper.quit();
        scraperThreadLocal.remove();
    }
}
