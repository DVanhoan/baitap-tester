package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;

import java.time.Duration;

public class BaseTests {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/";

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.navigate().to(BASE_URL);

        System.out.println("Setup hoàn tất. Tiêu đề trang: " + driver.getTitle());
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Teardown hoàn tất. Browser đã đóng.");
        }
    }

    protected HomePage goToHomePage() {
        driver.navigate().to(BASE_URL);
        return new HomePage(driver);
    }
}
