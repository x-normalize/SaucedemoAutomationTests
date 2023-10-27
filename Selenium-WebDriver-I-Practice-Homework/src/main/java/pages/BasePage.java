package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected static WebDriver driver;
    protected WebDriverWait wait;
    private String pageUrl;

    public BasePage(WebDriver webDriver, WebDriverWait driverWait, String pageSpecificUrl) {
        driver = webDriver;
        wait = driverWait;
        pageUrl = pageSpecificUrl;
    }

    public String getPageUrl() {
        return basePageUrl + pageUrl;
    }

    // Url
    public String basePageUrl = "https://www.saucedemo.com/";


    // Methods
    public void navigate() {
        driver.get(getPageUrl());
    }

    public void assertNavigated() {
        Assertions.assertEquals(getPageUrl(), driver.getCurrentUrl(), "Page was not navigated.");
    }
}
