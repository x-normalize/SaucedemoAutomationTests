package sausedemotests;

import basesetup.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static basesetup.BaseTestSetup.fillDetailsForm;
import static pages.CheckoutCompletePage.*;
import static pages.Constants.*;

public class ProductsTests extends BaseTest {

    @BeforeEach
    public void beforeTest() {
        loginPage.navigate();
        loginPage.fillLoginForm("standard_user", "secret_sauce");
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        inventoryPage.addProductByTitle(Products.BACKPACK.getTitle());
        inventoryPage.addProductByTitle(Products.SHIRT.getTitle());

        inventoryPage.clickShoppingCartLink();

        // Assert Items and Totals
        var items = driver.findElements(By.className("inventory_item_name"));

        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        Assertions.assertEquals(Products.BACKPACK.getTitle(), items.get(0).getText(), "Item title not as expected");
        Assertions.assertEquals(Products.SHIRT.getTitle(), items.get(1).getText(), "Item title not as expected");
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        inventoryPage.addProductByTitle(Products.BACKPACK.getTitle());
        inventoryPage.addProductByTitle(Products.SHIRT.getTitle());
        inventoryPage.clickShoppingCartLink();

        // Assert Items and Totals
        driver.findElement(By.id("checkout")).click();

        // fill form
        fillDetailsForm();

        // Click on Continue
        WebElement continueButton = driver.findElement(By.xpath(CONTINUE_BUTTON));
        continueButton.click();

        // Assert Items and Prices
        assertCurrentPageUrl(OVERVIEW_PAGE_URL, driver.getCurrentUrl());
        assertItemsAndPrices();
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        inventoryPage.addProductByTitle(Products.BACKPACK.getTitle());
        inventoryPage.addProductByTitle(Products.SHIRT.getTitle());

        Assertions.assertEquals(2, inventoryPage.getShoppingCartItemsCount(),
                "shopping cart Items were not added.");

        inventoryPage.clickShoppingCartLink();

        // Assert Items and Totals
        driver.findElement(By.id("checkout")).click();

        // fill form
        fillDetailsForm();

        // Click on Continue
        WebElement continueButton = driver.findElement(By.xpath(CONTINUE_BUTTON));
        continueButton.click();

        // Assert Overview Page
        WebElement finishButton = driver.findElement(By.xpath(FINISH_BUTTON));
        finishButton.click();

        // Assert Checkout complete page
        assertCurrentPageUrl("https://www.saucedemo.com/checkout-complete.html", driver.getCurrentUrl());

        //Assert No Items
        assertNoItems();

        //Assert Complete Header Message
        Assertions.assertEquals("Thank you for your order!",
                driver.findElement(By.xpath(COMPLETE_HEADER)).getText(),
                "Wrong complete header");

    }
}