package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SimpleBrowserTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        // This setup is for a "headless" run, common in CI.
        // It assumes 'chromedriver' is in the system PATH.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Runs Chrome without a visible UI
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Test Case 1
    @Test
    void testGooglePageTitle() {
        driver.get("https://www.google.com");
        String title = driver.getTitle();
        System.out.println("Page title is: " + title);
        assertEquals("Google", title);
    }

    // Test Case 2
    @Test
    void testGoogleSearch() {
        driver.get("https://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Jenkins CI");
        searchBox.submit();
        driver.getTitle(); // Wait for page to load
        assertTrue(driver.getTitle().startsWith("Jenkins CI"));
    }

    // Test Case 3
    @Test
    void testGoogleAboutLink() {
        driver.get("https://www.google.com");
        WebElement aboutLink = driver.findElement(By.linkText("About"));
        aboutLink.click();
        assertTrue(driver.getTitle().contains("About"));
    }
}
