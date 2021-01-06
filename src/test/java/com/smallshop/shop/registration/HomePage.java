package com.smallshop.shop.registration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    private final static String WEB_HOST = "https://mysshop.herokuapp.com/";

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void open() {
        driver.get(WEB_HOST);
    }

    public String getTitle() {
        return driver.getTitle();
    }

}
