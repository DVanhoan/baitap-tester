package login;

import base.BaseTests;
import org.junit.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SecureAreaPage;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Bài 5: Testing with Junit
 * Các test case kiểm tra đăng nhập với các kịch bản khác nhau
 */
public class TestLoginScenarios extends BaseTests {

    @Test
    public void testLoginWithCorrectCredentials() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickFormAuthentication();

        loginPage.setUsername("tomsmith");
        loginPage.setPassword("SuperSecretPassword!");
        SecureAreaPage secureAreaPage = loginPage.clickLoginButton();

        String alertText = secureAreaPage.getAlertText();
        assertTrue("Đăng nhập thành công - alert phải chứa 'You logged in'",
                alertText.contains("You logged into a secure area!"));

        System.out.println("✓ TC1: Đăng nhập với thông tin đúng - PASS");
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickFormAuthentication();

        loginPage.setUsername("tomsmith");
        loginPage.setPassword("WrongPassword");
        loginPage.clickLoginButton();

        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));
        String alertText = driver.findElement(org.openqa.selenium.By.id("flash")).getText();

        assertTrue("Đăng nhập thất bại - alert phải chứa 'Invalid credentials'",
                alertText.contains("Invalid credentials"));

        System.out.println("✓ TC2: Đăng nhập với mật khẩu sai - PASS");
    }

    @Test
    public void testLoginWithEmptyUsername() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickFormAuthentication();

        loginPage.setPassword("SuperSecretPassword!");
        loginPage.clickLoginButton();

        String alertText = driver.findElement(org.openqa.selenium.By.id("flash")).getText();
        assertTrue("Username trống - alert phải chứa 'Invalid credentials'",
                alertText.contains("Invalid credentials"));

        System.out.println("✓ TC3: Đăng nhập với username trống - PASS");
    }

    @Test
    public void testLogoutAfterSuccessfulLogin() {
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickFormAuthentication();

        loginPage.setUsername("tomsmith");
        loginPage.setPassword("SuperSecretPassword!");
        SecureAreaPage secureAreaPage = loginPage.clickLoginButton();

        assertTrue("Alert phải hiển thị", secureAreaPage.getAlertText().contains("You logged in"));

        secureAreaPage.logout();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(2));

        System.out.println("✓ TC4: Đăng xuất thành công - PASS");
    }
}
