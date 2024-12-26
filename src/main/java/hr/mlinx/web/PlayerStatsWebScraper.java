package hr.mlinx.web;

import hr.mlinx.data.CourtType;
import hr.mlinx.exception.CookieDismissalException;
import hr.mlinx.io.output.PleaseWaitOutput;
import hr.mlinx.web.factory.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

public class PlayerStatsWebScraper {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private boolean cookieDismissed = false;

    private static final Map<CourtType, String> FILTER_VALUES = new EnumMap<>(CourtType.class);
    private static final Map<String, String> FILTER_SELECTORS = new HashMap<>();

    static {
        FILTER_VALUES.put(CourtType.BACKCOURT, "et_1");
        FILTER_VALUES.put(CourtType.FRONTCOURT, "et_2");

        FILTER_SELECTORS.put("et_1", "#filter > optgroup:nth-child(2) > option:nth-child(1)");
        FILTER_SELECTORS.put("et_2", "#filter > optgroup:nth-child(2) > option:nth-child(2)");
    }

    public PlayerStatsWebScraper() {
        PleaseWaitOutput.waitFor("initializing", this.getClass().getSimpleName());
        System.out.println("Return to the program when data collection stops...");
        driver = WebDriverFactory.getChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private void dismissCookiePopup() throws CookieDismissalException {
        if (cookieDismissed) return;

        try {
            WebElement dismissButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-reject-all-handler")));
            dismissButton.click();

            System.out.println("Cookie popup dismissed successfully");
            cookieDismissed = true;
        } catch (Exception e) {
            throw new CookieDismissalException(e);
        }
    }

    public List<String> scrapeContents(CourtType courtType) throws CookieDismissalException, InterruptedException {
        PleaseWaitOutput.waitFor("scraping stats for", courtType.getCourtName());
        String filterValue = FILTER_VALUES.get(courtType);
        if (filterValue == null) throw new IllegalArgumentException(courtType + " court type does not exist");

        List<String> contents = new ArrayList<>();

        driver.get("https://nbafantasy.nba.com/statistics");
        dismissCookiePopup();
        driver.navigate().refresh();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("root")));

        // sometimes a filter value was not found, and sometimes it was no problem,
        // not sure why though, even with the use of WebDriverWait, so waiting manually a little helps...
        // also: for some slower PCs or browsers this might not be enough
        Thread.sleep(1000);

        WebElement filterSelectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("filter")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(FILTER_SELECTORS.get(filterValue))));
        Select filterSelect = new Select(filterSelectElement);
        filterSelect.selectByValue(filterValue);

        WebElement sortSelectElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sort")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#sort > option:nth-child(15)")));
        Select sortSelect = new Select(sortSelectElement);
        sortSelect.selectByValue("points_per_game");

        WebElement nextButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#root > div:nth-child(3) > div > div > div.sc-bdnxRM.sc-gtsrHT.eVZJvz.gfuSqG > button:nth-child(4)")));

        while (true) {
            WebElement tableElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
            contents.add(tableElement.getText());

            if (!nextButton.isEnabled()) {
                break;
            }

            nextButton.click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
        }

        return contents;
    }

    public void quit() {
        driver.quit();
    }
}
