package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SecureAreaPage {
    private WebDriver driver;
    private By logoutButton = By.cssSelector("a.button");
    private By alertBox = By.id("flash");

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getAlertText() {
        WebElement alert = driver.findElement(alertBox);
        return alert.getText();
    }

    public void logout() {
        driver.findElement(logoutButton).click();
    }

    public boolean isAlertDisplayed() {
        try {
            return driver.findElement(alertBox).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
