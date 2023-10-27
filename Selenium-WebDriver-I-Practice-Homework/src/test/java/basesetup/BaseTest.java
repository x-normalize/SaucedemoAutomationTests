package basesetup;

import enums.BrowserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CheckoutCompletePage;
import pages.CheckoutOverviewPage;
import pages.InventoryPage;
import pages.LoginPage;

import java.time.Duration;

public class BaseTest {

    public static WebDriver driver;
    public static WebDriverWait wait;
    public LoginPage loginPage;
    public InventoryPage inventoryPage;
    public CheckoutOverviewPage checkoutOverviewPage;
    public CheckoutCompletePage checkoutCompletePage;

    @AfterEach
    public void afterTest() {
        // close driver
        driver.close();
    }

    @BeforeEach
    public void beforeTests() {
        driver = startBrowser(BrowserType.GOOGLE_CHROME);

        // Configure wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        loginPage = new LoginPage(driver, wait);
        inventoryPage = new InventoryPage(driver, wait);

        // Navigate to saucedemo.com
        driver.get("https://www.saucedemo.com/");
    }

    protected static WebDriver startBrowser(BrowserType browserType) {
        // Setup Browser
        switch (browserType) {
            case GOOGLE_CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                return new ChromeDriver(chromeOptions);
            case MOZILLA_FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                return new FirefoxDriver(firefoxOptions);
            case MICROSOFT_EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                return new EdgeDriver(edgeOptions);
        }

        return null;
    }

}