package hr.mlinx.api.filter;

import hr.mlinx.api.cache.service.CachingScraperService;
import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScraperCleanupFilter implements Filter {
    private final CachingScraperService cachingScraperService;

    public ScraperCleanupFilter(CachingScraperService cachingScraperService) {
        this.cachingScraperService = cachingScraperService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(request, response);
        } finally {
            cachingScraperService.cleanup();
        }
    }
}
