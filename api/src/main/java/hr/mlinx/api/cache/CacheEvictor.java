package hr.mlinx.api.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class CacheEvictor {
    private static final Logger log = LoggerFactory.getLogger(CacheEvictor.class);

    @CacheEvict(value = "playerStats", allEntries = true)
    @Scheduled(cron = "0 0 9 * * ?", zone = "PST") // runs daily at 9 AM PST
    public void clearCache() {
        log.info("Cleared cache!");
    }
}
