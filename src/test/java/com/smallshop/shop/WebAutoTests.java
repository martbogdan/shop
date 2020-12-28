package com.smallshop.shop;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class WebAutoTests extends WebDriverSettings{
    private final static String WEB_HOST = "https://mysshop.herokuapp.com/";
    private final static String FIRST_NAME = "Bogdan";
    private final static String LAST_NAME = "Mart";

    @Test
    public void checkTitle() {
        driver.get(WEB_HOST);
        String title = driver.getTitle();

        Assert.assertEquals("Shop", title);
    }

    @Test
    public void registrationTest() {
        driver.get(WEB_HOST);
        driver.findElement(By.cssSelector("[href=\"/registration\"]")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class=\"row\"]")));

        int n = new Random().nextInt(100) + 1;

        driver.findElement(By.id("username")).sendKeys(FIRST_NAME + n + LAST_NAME);
        driver.findElement(By.id("firstName")).sendKeys(FIRST_NAME);
        driver.findElement(By.id("lastName")).sendKeys(LAST_NAME);
        driver.findElement(By.id("email")).sendKeys(LAST_NAME + n + "test@mail.com");
        driver.findElement(By.id("phoneNumber")).sendKeys("1324578961");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.cssSelector("[class=\"btn btn-primary btn-block\"]")).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[action=\"/login\"]")));
    }

}
