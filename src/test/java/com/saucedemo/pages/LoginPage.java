package com.saucedemo.pages;

import com.saucedemo.utils.Commons;
import com.saucedemo.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    private WebDriver driver;

    // Page Factory Elements
    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    @FindBy(css = ".error-button")
    private WebElement errorCloseButton;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        WaitUtils.waitForElementToBeVisible(driver, usernameField);
        Commons.sendKeys(usernameField, username);
        return this;
    }

    @Step("Enter password: {password}")
    public LoginPage enterPassword(String password) {
        Commons.sendKeys(passwordField, password);
        return this;
    }

    @Step("Click login button")
    public ProductsPage clickLoginButton() {
        WaitUtils.waitForElementToBeClickable(driver, loginButton);
        Commons.click(loginButton);
        return new ProductsPage(driver);
    }

    @Step("Login with credentials: {username}")
    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        return clickLoginButton();
    }

    @Step("Get error message")
    public String getErrorMessage() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, errorMessage);
            return Commons.getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Check if error message is displayed")
    public boolean isErrorMessageDisplayed() {
        return Commons.isDisplayed(errorMessage);
    }

    @Step("Close error message")
    public LoginPage closeErrorMessage() {
        if (isErrorMessageDisplayed()) {
            Commons.click(errorCloseButton);
        }
        return this;
    }

    @Step("Check if login page is displayed")
    public boolean isLoginPageDisplayed() {
        return Commons.isDisplayed(usernameField) &&
               Commons.isDisplayed(passwordField) &&
               Commons.isDisplayed(loginButton);
    }
}
