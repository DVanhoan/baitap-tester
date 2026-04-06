import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class ECommerceEndToEndTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private String randomEmail = "hoan.tester" + System.currentTimeMillis() + "@gmail.com";
    private String password = "Password@123456"; // Đổi pass dài hơn một chút cho an toàn

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // TĂNG thời gian chờ lên 20 giây để bù trừ mạng chậm
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @Test(priority = 1)
    public void testSignup() {
        driver.get("https://demo.nopcommerce.com/");
        wait.until(ExpectedConditions.elementToBeClickable(By.className("ico-register"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName"))).sendKeys("Hoan");
        driver.findElement(By.id("LastName")).sendKeys("Duong");
        driver.findElement(By.id("Email")).sendKeys(randomEmail);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);

        driver.findElement(By.id("register-button")).click();

        // Chờ thông báo thành công
        WebElement resultMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("result")));
        Assert.assertEquals(resultMsg.getText(), "Your registration completed");

        driver.findElement(By.className("ico-logout")).click();
    }

    // dependsOnMethods: Nếu testSignup fail, bài test này sẽ bị Skip chứ không cố chạy để sinh ra lỗi giả
    @Test(priority = 2, dependsOnMethods = "testSignup")
    public void testLogin() {
        driver.get("https://demo.nopcommerce.com/"); // Đảm bảo luôn bắt đầu từ trang chủ
        wait.until(ExpectedConditions.elementToBeClickable(By.className("ico-login"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email"))).sendKeys(randomEmail);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.cssSelector("button.login-button")).click();

        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ico-account"))).isDisplayed());
    }

    @Test(priority = 3, dependsOnMethods = "testLogin")
    public void testSearchAndLikeProduct() {
        driver.get("https://demo.nopcommerce.com/");
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("small-searchterms")));
        searchBox.sendKeys("MacBook");
        driver.findElement(By.cssSelector("button.search-box-button")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".product-title a"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-wishlist-button-4"))).click();

        WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bar-notification.success")));
        Assert.assertTrue(notification.getText().contains("The product has been added to your wishlist"));
        driver.findElement(By.cssSelector(".close")).click(); // Tắt thông báo
    }

    @Test(priority = 4, dependsOnMethods = "testLogin")
    public void testAddToCart() {
        // Mở lại trang sản phẩm để chắc chắn có nút Add to Cart
        driver.get("https://demo.nopcommerce.com/apple-macbook-pro-13-inch");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button-4"))).click();

        WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".bar-notification.success")));
        Assert.assertTrue(notification.getText().contains("The product has been added to your shopping cart"));
        driver.findElement(By.cssSelector(".close")).click();
    }

    @Test(priority = 5, dependsOnMethods = "testAddToCart")
    public void testOrderCheckout() {
        driver.get("https://demo.nopcommerce.com/cart");

        wait.until(ExpectedConditions.elementToBeClickable(By.id("termsofservice"))).click();
        driver.findElement(By.id("checkout")).click();

        // Các bước Checkout (chỉ chạy mượt nếu mạng ổn định)
        WebElement countryDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("BillingNewAddress_CountryId")));
        Select selectCountry = new Select(countryDropdown);
        selectCountry.selectByVisibleText("Vietnam");

        driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Da Nang");
        driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("VKU Campus");
        driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("550000");
        driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("0123456789");

        driver.findElement(By.cssSelector("button.new-address-next-step-button")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.shipping-method-next-step-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.payment-method-next-step-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.payment-info-next-step-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.confirm-order-next-step-button"))).click();

        WebElement orderSuccessMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".title strong")));
        Assert.assertEquals(orderSuccessMsg.getText(), "Your order has been successfully processed!");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}