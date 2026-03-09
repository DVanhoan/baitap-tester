package login;

import base.BaseTests;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.HomePage;
import pages.LoginPage;
import pages.SecureAreaPage;

import static junit.framework.Assert.assertTrue;

/**
 * Bài 4: Wait for loading
 * Kiểm tra sử dụng Explicit Wait để chờ phần tử xuất hiện
 */
public class TestLoginWithWait extends BaseTests {

    @Test
    public void testLoginWithExplicitWait() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickFormAuthentication();
        loginPage.setUsername("tomsmith");
        loginPage.setPassword("SuperSecretPassword!");
        SecureAreaPage secureAreaPage = loginPage.clickLoginButton();

        String alertText = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.id("flash"))).getText();

        assertTrue("Alert text không chứa success message",
                alertText.contains("You logged into a secure area!"));

        System.out.println("✓ Test với Explicit Wait thành công!");
        System.out.println("Alert text: " + alertText);
    }

    @Test
    public void testWaitForSecureAreaPageLoad() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickFormAuthentication();

        loginPage.setUsername("tomsmith");
        loginPage.setPassword("SuperSecretPassword!");
        SecureAreaPage secureAreaPage = loginPage.clickLoginButton();

        // Chờ cho đến khi logout button có thể nhấp được (Explicit Wait)
        wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("a.button")));

        assertTrue("Logout button không xuất hiện", secureAreaPage.isAlertDisplayed());
        System.out.println("✓ Test chờ trang tải hoàn tất!");
    }
}
