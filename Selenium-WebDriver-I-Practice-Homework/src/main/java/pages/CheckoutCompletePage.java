package pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class CheckoutCompletePage extends BasePage {

    public CheckoutCompletePage(WebDriver webDriver, WebDriverWait driverWait) {
        super(webDriver, driverWait, "checkout-complete.html");
    }

    public static void assertCurrentPageUrl(String expectedUrl, String currentUrl) {
        Assertions.assertEquals(expectedUrl, currentUrl, "Navigated to the wrong page");
    }

    public static void assertProductTitleEquals(String xpath, String expectedTitle) {
        String actualTitle = driver.findElement(By.xpath(xpath)).getText();
        Assertions.assertEquals(expectedTitle, actualTitle, "Product title mismatch. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }

    protected static void assertProductPriceEquals(String xpath, double expectedPrice) {
        double actualPrice = getProductPriceFromElement(xpath);
        Assertions.assertEquals(expectedPrice, actualPrice, "Product price mismatch. Expected: " + expectedPrice + ", Actual: " + actualPrice);
    }

    protected static double getProductPriceFromElement(String xpath) {
        return Double.parseDouble(driver.findElement(By.xpath(xpath)).getText().substring(1));
    }

    public static void assertItemsAndPrices() {
        ArrayList<WebElement> items = new ArrayList<>(driver.findElements(By.className("inventory_item_name")));
        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        assertProductTitleEquals("(//div[@class='inventory_item_name'])[1]", "Sauce Labs Backpack");
        assertProductTitleEquals("(//div[@class='inventory_item_name'])[2]", "Sauce Labs Bolt T-Shirt");
        assertProductPriceEquals("(//div[@class='inventory_item_price'])[1]", 29.99);
        assertProductPriceEquals("(//div[@class='inventory_item_price'])[2]", 15.99);
    }

    public static void assertNoItems() {
        ArrayList<WebElement> items = new ArrayList<>(driver.findElements(By.className("inventory_item_name")));
        Assertions.assertEquals(0, items.size(), "Items count not as expected");
    }
}
