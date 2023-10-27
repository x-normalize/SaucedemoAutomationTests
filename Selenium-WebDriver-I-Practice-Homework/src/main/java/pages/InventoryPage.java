package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class InventoryPage extends BasePage {

    public InventoryPage(WebDriver webDriver, WebDriverWait driverWait) {
        super(webDriver, driverWait, "inventory.html");
    }

    // locators
    public By shoppingCartLink = By.className("shopping_cart_link");
    public By pageTitle = By.xpath("//span[@class='title' and text()='Products']");


    // methods
    public void addProductByTitle(String title) {
        var container = driver.findElement(By.xpath(String.format("//div[@class='inventory_item' and descendant::div[text()='%s']]", title)));
        container.findElement(By.className("btn_inventory")).click();
    }

    public void clickShoppingCartLink() {
        driver.findElement(shoppingCartLink).click();
    }

    public Integer getShoppingCartItemsCount() {
        String cartValue = driver.findElement(shoppingCartLink).getText();
        if (cartValue.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(cartValue);
        }
    }

    public void waitForPageTitle() {
        var title = driver.findElement(pageTitle);
        wait.until(ExpectedConditions.visibilityOf(title));
    }
}
