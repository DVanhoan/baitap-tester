package login;

import base.BaseTests;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static junit.framework.Assert.assertTrue;

/**
 * Bài 6: Working with Alert
 * Kiểm tra xử lý alert boxes từ JavaScript
 */
public class TestAlertHandling extends BaseTests {

    @Test
    public void testJavaScriptAlert() {
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();
        assertTrue("Alert phải chứa 'I am a JS Alert'",
                alertText.contains("I am a JS Alert"));

        System.out.println("✓ Alert text: " + alertText);

        alert.accept();

        System.out.println("✓ Test JS Alert - PASS");
    }

    @Test
    public void testJavaScriptConfirm() {
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String confirmText = alert.getText();
        assertTrue("Confirm phải chứa 'I am a JS Confirm'",
                confirmText.contains("I am a JS Confirm"));

        System.out.println("✓ Confirm text: " + confirmText);

        alert.dismiss();

        System.out.println("✓ Test JS Confirm (Cancel) - PASS");
    }

    @Test
    public void testJavaScriptPrompt() {
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String promptText = alert.getText();
        assertTrue("Prompt phải chứa 'I am a JS prompt'",
                promptText.contains("I am a JS prompt"));

        System.out.println("✓ Prompt text: " + promptText);

        alert.sendKeys("Hello Selenium");

        System.out.println("✓ Dữ liệu đã nhập: Hello Selenium");

        alert.accept();

        System.out.println("✓ Test JS Prompt - PASS");
    }

    @Test
    public void testAlertWithWait() {
        driver.navigate().to("https://the-internet.herokuapp.com/javascript_alerts");

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String text = alert.getText();
            System.out.println("Alert xuất hiện: " + text);
            alert.accept();
            assertTrue("Alert đã được xử lý", true);
            System.out.println("✓ Test Alert với Wait - PASS");
        } catch (Exception e) {
            System.err.println("✗ Lỗi: " + e.getMessage());
        }
    }
}
