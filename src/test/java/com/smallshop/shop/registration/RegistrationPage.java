package com.smallshop.shop.registration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    private final By registrationLocator = By.cssSelector("[href=\"/registration\"]");
    private final By mainRegistration = By.cssSelector("[class=\"row\"]");
    private final By registrationComplete = By.cssSelector("[action=\"/login\"]");
    private final By xpathParent = By.xpath("..");
    private final By errorMessageLocator = By.cssSelector("label");

    @FindBy(id = "username")
    private WebElement username;
    @FindBy(id = "firstName")
    private WebElement firstName;
    @FindBy(id = "lastName")
    private WebElement lastName;
    @FindBy(id = "email")
    private WebElement email;
    @FindBy(id = "phoneNumber")
    private WebElement phoneNumber;
    @FindBy(id = "password")
    private WebElement password;
    @FindBy(css = "[class=\"btn btn-primary btn-block\"]")
    private WebElement submitButton;

    public void openRegistrationForm() {
        driver.findElement(registrationLocator).click();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(mainRegistration));
    }

    public void fillUsername(String nickName) {
        username.sendKeys(nickName);
    }

    public void fillFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
    }

    public void fillLastName(String lastName) {
        this.lastName.sendKeys(lastName);
    }

    public void fillEmail(String email) {
        this.email.sendKeys(email);
    }

    public void fillPhoneNumber(String phoneNumber) {
        this.phoneNumber.sendKeys(phoneNumber);
    }

    public void fillPassword(String password) {
        this.password.sendKeys(password);
    }

    public void submitForm() {
        submitButton.click();
    }

    public void checkRegistrationSuccess() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(registrationComplete));
    }

    public String getErrorUsernameMessage() {
        WebElement userNameParent = username.findElement(xpathParent);
        return userNameParent.findElement(errorMessageLocator).getText();
    }

    public String getErrorFirstNameMessage() {
        WebElement firstNameParent = firstName.findElement(xpathParent);
        return firstNameParent.findElement(errorMessageLocator).getText();
    }

    public String getErrorLastNameMessage() {
        WebElement lastNameParent = lastName.findElement(xpathParent);
        return lastNameParent.findElement(errorMessageLocator).getText();
    }

    public String getErrorEmailMessage() {
        WebElement emailParent = email.findElement(xpathParent);
        return emailParent.findElement(errorMessageLocator).getText();
    }

    public String getErrorPhoneMessage() {
        WebElement phoneParent = phoneNumber.findElement(xpathParent);
        return phoneParent.findElement(errorMessageLocator).getText();
    }

    public String getErrorPasswordMessage() {
        WebElement passwordParent = password.findElement(xpathParent);
        return passwordParent.findElement(errorMessageLocator).getText();
    }
}
