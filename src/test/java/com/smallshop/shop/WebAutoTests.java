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
    private final static String ERROR_MESSAGE_1 = "Must not be blank";
    private final static String ERROR_MESSAGE_2 = "розмір має бути між %s та %s";

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
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class=\"row\"]")));

        int n = new Random().nextInt(100) + 1;

        driver.findElement(By.id("username")).sendKeys(FIRST_NAME + n + LAST_NAME);
        driver.findElement(By.id("firstName")).sendKeys(FIRST_NAME);
        driver.findElement(By.id("lastName")).sendKeys(LAST_NAME);
        driver.findElement(By.id("email")).sendKeys(LAST_NAME + n + "test@mail.com");
        driver.findElement(By.id("phoneNumber")).sendKeys("1324578961");
        driver.findElement(By.id("password")).sendKeys("12345");
        driver.findElement(By.cssSelector("[class=\"btn btn-primary btn-block\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[action=\"/login\"]")));
    }

    @Test
    public void registrationFailureTest() {
        driver.get(WEB_HOST);
        driver.findElement(By.cssSelector("[href=\"/registration\"]")).click();

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class=\"row\"]")));

        driver.findElement(By.cssSelector("[class=\"btn btn-primary btn-block\"]")).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[class=\"validation-message\"]")));

        WebElement userName = driver.findElement(By.id("username"));
        WebElement userNameParent = userName.findElement(By.xpath(".."));
        String actualUserNameMessage = userNameParent.findElement(By.cssSelector("label")).getText();
        Assert.assertTrue(actualUserNameMessage.contains(String.format(ERROR_MESSAGE_2, 1, 100))
                && actualUserNameMessage.contains(ERROR_MESSAGE_1));

        WebElement firstName = driver.findElement(By.id("firstName"));
        WebElement firstNameParent = firstName.findElement(By.xpath(".."));
        String actualFirstName = firstNameParent.findElement(By.cssSelector("label")).getText();
        Assert.assertTrue(actualFirstName.contains(String.format(ERROR_MESSAGE_2, 1, 100))
                && actualFirstName.contains(ERROR_MESSAGE_1));

        WebElement lastName = driver.findElement(By.id("lastName"));
        WebElement lastNameParent = lastName.findElement(By.xpath(".."));
        String actualLastName = lastNameParent.findElement(By.cssSelector("label")).getText();
        Assert.assertTrue(actualLastName.contains(String.format(ERROR_MESSAGE_2, 1, 100))
                && actualLastName.contains(ERROR_MESSAGE_1));

        WebElement email = driver.findElement(By.id("email"));
        WebElement emailParent = email.findElement(By.xpath(".."));
        String actualEmailMessage = emailParent.findElement(By.cssSelector("label")).getText();
        Assert.assertEquals(ERROR_MESSAGE_1, actualEmailMessage);

        WebElement phone = driver.findElement(By.id("phoneNumber"));
        WebElement phoneParent = phone.findElement(By.xpath(".."));
        String actualPhoneMessage = phoneParent.findElement(By.cssSelector("label")).getText();
        Assert.assertTrue(actualPhoneMessage.contains(String.format(ERROR_MESSAGE_2, 10, 12))
                && actualPhoneMessage.contains(ERROR_MESSAGE_1));

        WebElement password = driver.findElement(By.id("password"));
        WebElement passwordParent = password.findElement(By.xpath(".."));
        String actualPasswordMessage = passwordParent.findElement(By.cssSelector("label")).getText();
        Assert.assertTrue(actualPasswordMessage.contains(String.format(ERROR_MESSAGE_2, 1, 100))
                && actualPasswordMessage.contains(ERROR_MESSAGE_1));
    }

}
