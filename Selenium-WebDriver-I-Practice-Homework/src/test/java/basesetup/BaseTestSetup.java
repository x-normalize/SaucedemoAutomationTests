package basesetup;

import enums.BrowserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;

import static utils.Constants.*;


public abstract class BaseTestSetup {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static Logger log = LogManager.getLogger();

    @AfterEach
    public void classTearDown() {
        resetAppState();
        driver.close();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected static void startWithBrowser(BrowserType browserType) {
        switch (browserType) {
            case MICROSOFT_EDGE:
                driver = new EdgeDriver();
                break;
            case MOZILLA_FIREFOX:
                driver = new FirefoxDriver();
                break;
            case GOOGLE_CHROME:
                driver = new ChromeDriver();
                break;
        }
    }

    public static void authenticateWithUser(String username, String pass) {
        WebElement usernameInput = driver.findElement(By.xpath("//input[@data-test='username']"));
        usernameInput.sendKeys(username);

        WebElement password = driver.findElement(By.xpath("//input[@data-test='password']"));
        password.sendKeys(pass);

        WebElement loginButton = driver.findElement(By.xpath("//input[@data-test='login-button']"));
        loginButton.click();

        WebElement inventoryPageTitle = driver.findElement(By.xpath("//div[@class='app_logo']"));
        wait.until(ExpectedConditions.visibilityOf(inventoryPageTitle));
    }

    protected void addBackpackAndTShirtToShoppingCart(BrowserType browserType) {
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

    }

    protected void completeCheckoutProcess() {

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

        assertItems();
    }

    public static void fillDetailsForm() {
        WebElement firstNameInput = driver.findElement(By.xpath(FIRST_NAME));
        firstNameInput.sendKeys("Yordan");
        WebElement lastNameInput = driver.findElement(By.xpath(LAST_NAME));
        lastNameInput.sendKeys("Nikolov");
        WebElement postalCodeInput = driver.findElement(By.xpath(POSTAL_CODE));
        postalCodeInput.sendKeys("7000");
    }

    protected static void assertCurrentPageUrl(String expectedUrl, String currentUrl) {
        Assertions.assertEquals(expectedUrl, currentUrl, "Navigated to the wrong page");
    }

    protected static void assertProductTitleEquals(String xpath, String expectedTitle) {
        String actualTitle = driver.findElement(By.xpath(xpath)).getText();
        Assertions.assertEquals(expectedTitle, actualTitle, "Product title mismatch. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }

    protected static double getProductPriceFromElement(String xpath) {
        return Double.parseDouble(driver.findElement(By.xpath(xpath)).getText().substring(1));
    }

    protected static void assertProductPriceEquals(String xpath, double expectedPrice) {
        double actualPrice = getProductPriceFromElement(xpath);
        Assertions.assertEquals(expectedPrice, actualPrice, "Product price mismatch. Expected: " + expectedPrice + ", Actual: " + actualPrice);
    }

    protected static void assertTotalPriceEquals(double expectedTotalSum, String... productXpaths) {
        double productsSum = 0;
        for (int i = 0; i < productXpaths.length; i++) {
            productsSum += Double.parseDouble(driver.findElement(By.xpath(productXpaths[i])).getText().substring(1));
        }
        Assertions.assertEquals(expectedTotalSum, productsSum, "Wrong total sum");
    }

    protected static void assertTotalPriceWithTaxEquals(String totalSumXpath, String taxXpath, double expectedTotalSum) {
        double expectedTax = expectedTotalSum * 0.08;
        double totalPrice = Double.parseDouble(
                driver.findElement(By.xpath("//div[@class='summary_subtotal_label']"))
                        .getText().substring(13));
        double tax = Double.parseDouble(
                driver.findElement(By.xpath("//div[@class='summary_tax_label']")).getText().substring(6));
        Assertions.assertEquals(String.format("%.2f", expectedTotalSum + expectedTax),
                String.valueOf(totalPrice + tax), "Wrong total price plus tax");
    }

    private static void resetAppState() {
        driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='reset_sidebar_link']"))).click();
    }

    protected static void assertItems() {
        log.info("Assert Items");
        ArrayList<WebElement> items = new ArrayList<>(driver.findElements(By.className("inventory_item_name")));
        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        Assertions.assertEquals("Sauce Labs Backpack",
                driver.findElement(By.xpath(CLASS_INVENTORY_ITEM_NAME)).getText(),
                "Wrong product title");
        Assertions.assertEquals("Sauce Labs Bolt T-Shirt",
                driver.findElement(By.xpath(DIV_CLASS_INVENTORY_ITEM_NAME)).getText(),
                "Wrong product title");
        log.info("Assert prices");
        assertProductPriceEquals("//*[@id='checkout_summary_container']/div/div[1]/div[3]/div[2]/div[2]/div", 29.99);
        assertProductPriceEquals("//*[@id='checkout_summary_container']/div/div[1]/div[4]/div[2]/div[2]/div",
                15.99);
        log.info("Assert Total Price and Tax");
        String[] productPricesXpaths = {"//*[@id='checkout_summary_container']/div/div[1]/div[3]/div[2]/div[2]/div",
                "//*[@id='checkout_summary_container']/div/div[1]/div[4]/div[2]/div[2]/div"};
        assertTotalPriceEquals(45.98, productPricesXpaths);
        assertTotalPriceWithTaxEquals("//div[@class='summary_subtotal_label']",
                "//div[@class='summary_tax_label']", 45.98);
    }

    protected static void assertItemsAndPrices() {
        ArrayList<WebElement> items = new ArrayList<>(driver.findElements(By.className("inventory_item_name")));
        Assertions.assertEquals(2, items.size(), "Items count not as expected");
        assertProductTitleEquals("(//div[@class='inventory_item_name'])[1]", "Sauce Labs Backpack");
        assertProductTitleEquals("(//div[@class='inventory_item_name'])[2]", "Sauce Labs Bolt T-Shirt");
        assertProductPriceEquals("(//div[@class='inventory_item_price'])[1]", 29.99);
        assertProductPriceEquals("(//div[@class='inventory_item_price'])[2]", 15.99);
    }

    protected static void assertNoItems() {
        ArrayList<WebElement> items = new ArrayList<>(driver.findElements(By.className("inventory_item_name")));
        Assertions.assertEquals(0, items.size(), "Items count not as expected");
    }

}
