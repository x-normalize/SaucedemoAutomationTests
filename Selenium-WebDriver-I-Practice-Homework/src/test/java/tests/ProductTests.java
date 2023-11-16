package tests;

import basesetup.BaseTestSetup;
import enums.BrowserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.sun.activation.registries.LogSupport.log;
import static utils.Constants.*;

public class ProductTests extends BaseTestSetup {

    @Test
    public void productAddedToShoppingCart_when_addToCart() {
        startWithBrowser(BrowserType.GOOGLE_CHROME);

        log("Navigate to https://www.saucedemo.com/");
        driver.get(WWW_SAUCEDEMO_COM);

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath(INPUT_DATA_TEST_USERNAME))));
        usernameInput.sendKeys(USER);

        WebElement passwordInput = driver.findElement(By.xpath(INPUT_DATA_TEST_PASSWORD));
        passwordInput.sendKeys(PASSWORD);

        WebElement loginButton = driver.findElement(By.xpath(LOGIN_BUTTON));
        loginButton.click();

        assertCurrentPageUrl(INVENTORY_PAGE_URL, driver.getCurrentUrl());

        log("Add Backpack and T-shirt to cart");
        WebElement backpackAddToCardButton = driver.findElement(By.xpath(ADD_TO_CART_SAUCE_LABS_BACKPACK));
        backpackAddToCardButton.click();
        WebElement tShirtAddToCardButton = driver.findElement(By.xpath(ADD_TO_CART_SAUCE_LABS_BOLT_T_SHIRT));
        tShirtAddToCardButton.click();

        log("Click on Shopping Cart");
        WebElement shoppingCartIcon = driver.findElement(By.xpath(SHOPPING_CART_LINK));
        shoppingCartIcon.click();

        log("Assert the current URL");
        assertCurrentPageUrl(SHOPPING_CART_PAGE_URL, driver.getCurrentUrl());

        log("Assert Items and Prices");
        assertItemsAndPrices();
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation() {
        startWithBrowser(BrowserType.MICROSOFT_EDGE);

        addBackpackAndTShirtToShoppingCart(BrowserType.MICROSOFT_EDGE);

        log("Click on Checkout");
        WebElement checkoutButton = driver.findElement(By.xpath(BUTTON_DATA_TEST_CHECKOUT));
        checkoutButton.click();

        log("Assert Fill Contact Details Page");
        assertCurrentPageUrl(USER_DETAILS_PAGE_URL, driver.getCurrentUrl());

        log("Fill Details");
        fillDetailsForm();

        log("Click on Continue");
        WebElement continueButton = driver.findElement(By.xpath(INPUT_DATA_TEST_CONTINUE));
        continueButton.click();

        log("Assert Overview Page");
        assertCurrentPageUrl(OVERVIEW_PAGE_URL, driver.getCurrentUrl());

        log("Assert Items");
        assertItems();
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm() {
        addBackpackAndTShirtToShoppingCart(BrowserType.GOOGLE_CHROME);
        completeCheckoutProcess();

        WebElement finishButton = driver.findElement(By.xpath(BUTTON_DATA_TEST_FINISH));
        finishButton.click();

        log("Assert Checkout complete page");
        assertCurrentPageUrl(CHECKOUT_COMPLETE_HTML, driver.getCurrentUrl());
        log("Assert No Items");
        assertNoItems();
        log("Assert Complete Header Message");
        Assertions.assertEquals("Thank you for your order!",
                driver.findElement(By.xpath(COMPLETE_HEADER)).getText(),
                "Wrong complete header");
    }
}
