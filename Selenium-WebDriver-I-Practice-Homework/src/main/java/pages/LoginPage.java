package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static pages.Constants.*;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver webDriver, WebDriverWait driverWait) {
        super(webDriver, driverWait, "");
    }

    // Locators
    public By usernameLocator = By.xpath(USERNAME);
    public By passwordLocator = By.xpath(PASSWORD);
    public By loginButtonLocator = By.xpath(LOGIN_BUTTON);

    // Methods
    public void fillLoginForm(String username, String pass) {
        WebElement usernameInput = driver.findElement(usernameLocator);
        usernameInput.sendKeys(username);

        WebElement password = driver.findElement(passwordLocator);
        password.sendKeys(pass);

        WebElement loginButton = driver.findElement(loginButtonLocator);
        loginButton.click();
    }
}