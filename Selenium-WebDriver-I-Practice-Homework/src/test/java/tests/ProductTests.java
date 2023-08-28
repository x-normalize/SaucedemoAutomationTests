package tests;

import basesetup.BaseTestSetup;
import enums.BrowserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static utils.Constants.*;

public class ProductTests extends BaseTestSetup {

    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void productAddedToShoppingCart_when_addToCart(BrowserType browserType) {

        startWithBrowser(browserType);

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        log.info("Navigate to https://www.saucedemo.com/");
        driver.get(WWW_SAUCEDEMO_COM);

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath(INPUT_DATA_TEST_USERNAME))));
        usernameInput.sendKeys(USER);

        WebElement passwordInput = driver.findElement(By.xpath(INPUT_DATA_TEST_PASSWORD));
        passwordInput.sendKeys(PASSWORD);

        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON));
        loginButton.click();

        assertCurrentPageUrl(INVENTORY_PAGE_URL, driver.getCurrentUrl());

        log.info("Add Backpack and T-shirt to cart");
        WebElement backpackAddToCardButton = driver.findElement(By.xpath(ADD_TO_CART_SAUCE_LABS_BACKPACK));
        backpackAddToCardButton.click();
        WebElement tShirtAddToCardButton = driver.findElement(By.xpath(ADD_TO_CART_SAUCE_LABS_BOLT_T_SHIRT));
        tShirtAddToCardButton.click();

        log.info("Click on Shopping Cart");
        WebElement shoppingCartIcon = driver.findElement(By.xpath(SHOPPING_CART_LINK));
        shoppingCartIcon.click();

        log.info("Assert the current URL");
        assertCurrentPageUrl(SHOPPING_CART_PAGE_URL, driver.getCurrentUrl());

        log.info("Assert Items and Prices");
        assertItemsAndPrices();

        log.info("Test 1 Passed Successfully!");
    }

    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void userDetailsAdded_when_checkoutWithValidInformation(BrowserType browserType) {

        addBackpackAndTShirtToShoppingCart(browserType);

        log.info("Click on Checkout");
        WebElement checkoutButton = driver.findElement(By.xpath(BUTTON_DATA_TEST_CHECKOUT));
        checkoutButton.click();

        log.info("Assert Fill Contact Details Page");
        assertCurrentPageUrl(USER_DETAILS_PAGE_URL, driver.getCurrentUrl());

        log.info("Fill Details");
        WebElement firstNameInput = driver.findElement(By.xpath(FIRST_NAME));
        firstNameInput.sendKeys("Yordan");
        WebElement lastNameInput = driver.findElement(By.xpath(LAST_NAME));
        lastNameInput.sendKeys("Nikolov");
        WebElement postalCodeInput = driver.findElement(By.xpath(POSTAL_CODE));
        postalCodeInput.sendKeys("7000");

        log.info("Click on Continue");
        WebElement continueButton = driver.findElement(By.xpath(INPUT_DATA_TEST_CONTINUE));
        continueButton.click();

        log.info("Assert Overview Page");
        assertCurrentPageUrl(OVERVIEW_PAGE_URL, driver.getCurrentUrl());

        log.info("Assert Items");
        assertItems();

        log.info("Test 2 Passed Successfully!");
    }

    @ParameterizedTest
    @EnumSource(BrowserType.class)
    public void orderCompleted_when_addProduct_and_checkout_withConfirm(BrowserType browserType) {

        addBackpackAndTShirtToShoppingCart(browserType);

        completeCheckoutProcess();

        WebElement finishButton = driver.findElement(By.xpath(BUTTON_DATA_TEST_FINISH));
        finishButton.click();

        log.info("Assert Checkout complete page");
        assertCurrentPageUrl("https://www.saucedemo.com/checkout-complete.html", driver.getCurrentUrl());
        log.info("Assert No Items");
        assertNoItems();
        log.info("Assert Complete Header Message");
        Assertions.assertEquals("Thank you for your order!",
                driver.findElement(By.xpath(COMPLETE_HEADER)).getText(),
                "Wrong complete header");

        log.info("Test 3 Passed Successfully!");
    }
}
