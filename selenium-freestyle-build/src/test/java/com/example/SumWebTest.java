package com.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class SumWebTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--allow-file-access-from-files");
        options.addArguments("--no-sandbox"); // Often needed in CI
        options.addArguments("--disable-dev-shm-usage"); // Often needed in CI
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // CRITICAL: This file path must be correct FOR THE JENKINS SERVER
        // Jenkins copies the repo into its workspace.
        // We will find the dynamic path instead of a static C: drive path.
        String path = System.getProperty("user.dir"); // Gets the Jenkins workspace folder
        String url = "file:///" + path + "/src/test/resources/sum.html";
        driver.get(url);
    }

    // Test Case 1: (From your PDF)
    @Test
    public void testSumPositiveNumbers() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("num1")));
        WebElement num1 = driver.findElement(By.id("num1"));
        WebElement num2 = driver.findElement(By.id("num2"));
        WebElement calcBtn = driver.findElement(By.id("calcBtn"));
        WebElement result = driver.findElement(By.id("result"));

        num1.sendKeys("10");
        num2.sendKeys("20");
        calcBtn.click();
        
        // Wait for the text to be updated
        wait.until(ExpectedConditions.textToBe(By.id("result"), "Sum = 30"));
        
        String output = result.getText().trim();
        System.out.println("Test Case 1 Output: " + output);
        assertEquals("Sum = 30", output);
    }

    // Test Case 2: (Added to meet requirements)
    @Test
    public void testSumWithZero() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("num1")));
        WebElement num1 = driver.findElement(By.id("num1"));
        WebElement num2 = driver.findElement(By.id("num2"));
        WebElement calcBtn = driver.findElement(By.id("calcBtn"));
        WebElement result = driver.findElement(By.id("result"));

        num1.sendKeys("99");
        num2.sendKeys("0");
        calcBtn.click();
        
        wait.until(ExpectedConditions.textToBe(By.id("result"), "Sum = 99"));
        
        String output = result.getText().trim();
        System.out.println("Test Case 2 Output: " + output);
        assertEquals("Sum = 99", output);
    }

    // Test Case 3: (Added to meet requirements)
    @Test
    public void testSumWithEmptyInputs() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("num1")));
        WebElement num1 = driver.findElement(By.id("num1"));
        WebElement num2 = driver.findElement(By.id("num2"));
        WebElement calcBtn = driver.findElement(By.id("calcBtn"));
        WebElement result = driver.findElement(By.id("result"));

        // Send empty strings
        num1.sendKeys("");
        num2.sendKeys("");
        calcBtn.click();
        
        // The Javascript code in your sum.html treats empty as 0
        wait.until(ExpectedConditions.textToBe(By.id("result"), "Sum = 0"));
        
        String output = result.getText().trim();
        System.out.println("Test Case 3 Output: " + output);
        assertEquals("Sum = 0", output);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
