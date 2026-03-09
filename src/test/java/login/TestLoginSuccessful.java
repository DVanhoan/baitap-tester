package login;

import base.BaseTests;
import org.junit.Test;
import pages.DemoQAPage;
import pages.HomePage;
import pages.LoginPage;
import pages.SecureAreaPage;

import static junit.framework.Assert.assertTrue;

public class TestLoginSuccessful extends BaseTests {

    @Test
    public void testLoginSuccessful() {
        // Bài 2 + 3: Điều hướng và tìm kiếm phần tử
        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = homePage.clickFormAuthentication();

        loginPage.setUsername("tomsmith");
        loginPage.setPassword("SuperSecretPassword!");

        SecureAreaPage secureAreaPage = loginPage.clickLoginButton();

        assertTrue("Alert text is incorrect",
                secureAreaPage.getAlertText().contains("You logged into a secure area!"));

        System.out.println("✓ Test đăng nhập thành công!");
    }
}
