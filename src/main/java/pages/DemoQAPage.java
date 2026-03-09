package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DemoQAPage {
    private WebDriver driver;

    private By firstNameInput = By.id("firstName");
    private By lastNameInput = By.id("lastName");

    private By emailInput = By.id("userEmail");

    private By buttonSubmit = By.id("submit");

    public DemoQAPage(WebDriver driver) {
        this.driver = driver;
        this.driver.get("https://demoqa.com/automation-practice-form");
    }

    public void setFirstName(String firstName) {
        driver.findElement(firstNameInput).sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        driver.findElement(lastNameInput).sendKeys(lastName);
    }

    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void clickSubmit() {
        driver.findElement(buttonSubmit).click();
    }
}
