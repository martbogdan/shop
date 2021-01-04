package com.smallshop.shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverSettings {
    public WebDriver driver;
    public WebDriverWait wait;

    private final static String CHROME_DRIVER = "src/main/resources/drivers/chromedriver87.0.4280.88";

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterEach
    public void close() {
        driver.quit();
    }
}
