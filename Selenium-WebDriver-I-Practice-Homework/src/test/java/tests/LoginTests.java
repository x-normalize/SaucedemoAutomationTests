package tests;

import basesetup.BaseTestSetup;
import enums.BrowserType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.Constants.INVENTORY_PAGE_URL;
import static utils.Constants.WWW_SAUCEDEMO_COM;

public class LoginTests extends BaseTestSetup {

    @BeforeAll
    public static void beforeAllTests() {
        startWithBrowser(BrowserType.GOOGLE_CHROME);

        // Configure wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Navigate to saucedemo.com
        driver.get(WWW_SAUCEDEMO_COM);
    }

    @Test
    public void userAuthenticated_when_validCredentialsProvided() {
        authenticateWithUser("standard_user", "secret_sauce");

        assertCurrentPageUrl(INVENTORY_PAGE_URL, driver.getCurrentUrl());

    }
}

