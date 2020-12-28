package com.smallshop.shop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverSettings {
    protected ChromeDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver87.0.4280.88");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void close() {
        driver.quit();
    }
}
