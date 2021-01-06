package com.smallshop.shop.registration;

import com.smallshop.shop.WebDriverSettings;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

public class WebAutoTests extends WebDriverSettings {
    private final static String FIRST_NAME = "Bogdan";
    private final static String LAST_NAME = "Mart";
    private final static String ERROR_MESSAGE_1 = "Must not be blank";
    private final static String ERROR_MESSAGE_2 = "розмір має бути між %s та %s";
    private static final String VALID_PHONE_NUMBER = "1234567890";
    private static final String PASSWORD = "12345";
    private static final String EXPECTED_TITLE = "Shop";

    @Test
    public void checkTitle() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.open();
        String actualTitle = homePage.getTitle();

        Assert.assertEquals(EXPECTED_TITLE, actualTitle);
    }

    @Test
    public void registrationTest() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        RegistrationPage registrationPage = PageFactory.initElements(driver, RegistrationPage.class);

        homePage.open();
        registrationPage.openRegistrationForm();

        int n = new Random().nextInt(100) + 1;

        registrationPage.fillUsername(FIRST_NAME + n + LAST_NAME);
        registrationPage.fillFirstName(FIRST_NAME);
        registrationPage.fillLastName(LAST_NAME);
        registrationPage.fillEmail(LAST_NAME + n + "test@mail.com");
        registrationPage.fillPhoneNumber(VALID_PHONE_NUMBER);
        registrationPage.fillPassword(PASSWORD);
        registrationPage.submitForm();
        registrationPage.checkRegistrationSuccess();
    }

    @Test
    public void registrationFailureTest() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        RegistrationPage registrationPage = PageFactory.initElements(driver, RegistrationPage.class);

        homePage.open();
        registrationPage.openRegistrationForm();
        registrationPage.submitForm();

        String usernameErrorMessage = registrationPage.getErrorUsernameMessage();
        Assert.assertTrue(usernameErrorMessage.contains(String.format(ERROR_MESSAGE_2, 1, 100))
                && usernameErrorMessage.contains(ERROR_MESSAGE_1));

        String firstNameErrorMessage = registrationPage.getErrorFirstNameMessage();
        Assert.assertTrue(firstNameErrorMessage.contains(String.format(ERROR_MESSAGE_2, 1, 100))
                && firstNameErrorMessage.contains(ERROR_MESSAGE_1));

        String lastNameErrorMessage = registrationPage.getErrorLastNameMessage();
        Assert.assertTrue(lastNameErrorMessage.contains(String.format(ERROR_MESSAGE_2, 1, 100))
                && lastNameErrorMessage.contains(ERROR_MESSAGE_1));

        String emailErrorMessage = registrationPage.getErrorEmailMessage();
        Assert.assertEquals(ERROR_MESSAGE_1, emailErrorMessage);

        String phoneErrorMessage = registrationPage.getErrorPhoneMessage();
        Assert.assertTrue(phoneErrorMessage.contains(String.format(ERROR_MESSAGE_2, 10, 12))
                && phoneErrorMessage.contains(ERROR_MESSAGE_1));

        String passwordErrorMessage = registrationPage.getErrorPasswordMessage();
        Assert.assertTrue(passwordErrorMessage.contains(String.format(ERROR_MESSAGE_2, 1, 100))
                && passwordErrorMessage.contains(ERROR_MESSAGE_1));
    }

}
