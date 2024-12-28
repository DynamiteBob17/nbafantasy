package hr.mlinx.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ScraperApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScraperApiApplication.class);
    }
}
