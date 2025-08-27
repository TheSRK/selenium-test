package com.saucedemo.pages;

import com.saucedemo.utils.Commons;
import com.saucedemo.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
    private WebDriver driver;

    // Page Factory Elements - Step One
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // Step Two Elements
    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(css = ".summary_tax_label")
    private WebElement taxLabel;

    @FindBy(css = ".summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    // Complete Page Elements
    @FindBy(css = ".complete-header")
    private WebElement completeHeader;

    @FindBy(css = ".complete-text")
    private WebElement completeText;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Enter first name: {firstName}")
    public CheckoutPage enterFirstName(String firstName) {
        WaitUtils.waitForElementToBeVisible(driver, firstNameField);
        Commons.sendKeys(firstNameField, firstName);
        return this;
    }

    @Step("Enter last name: {lastName}")
    public CheckoutPage enterLastName(String lastName) {
        Commons.sendKeys(lastNameField, lastName);
        return this;
    }

    @Step("Enter postal code: {postalCode}")
    public CheckoutPage enterPostalCode(String postalCode) {
        Commons.sendKeys(postalCodeField, postalCode);
        return this;
    }

    @Step("Fill checkout information")
    public CheckoutPage fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return this;
    }

    @Step("Click continue button")
    public CheckoutPage clickContinue() {
        WaitUtils.waitForElementToBeClickable(driver, continueButton);
        Commons.click(continueButton);
        return this;
    }

    @Step("Click cancel button")
    public CartPage clickCancel() {
        WaitUtils.waitForElementToBeClickable(driver, cancelButton);
        Commons.click(cancelButton);
        return new CartPage(driver);
    }

    @Step("Get checkout error message")
    public String getErrorMessage() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, errorMessage);
            return Commons.getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    @Step("Get page title")
    public String getPageTitle() {
        WaitUtils.waitForElementToBeVisible(driver, pageTitle);
        return Commons.getText(pageTitle);
    }

    @Step("Get order subtotal")
    public String getSubtotal() {
        WaitUtils.waitForElementToBeVisible(driver, subtotalLabel);
        return Commons.getText(subtotalLabel);
    }

    @Step("Get order tax")
    public String getTax() {
        WaitUtils.waitForElementToBeVisible(driver, taxLabel);
        return Commons.getText(taxLabel);
    }

    @Step("Get order total")
    public String getTotal() {
        WaitUtils.waitForElementToBeVisible(driver, totalLabel);
        return Commons.getText(totalLabel);
    }

    @Step("Click finish button")
    public CheckoutPage clickFinish() {
        WaitUtils.waitForElementToBeClickable(driver, finishButton);
        Commons.click(finishButton);
        return this;
    }

    @Step("Get completion header")
    public String getCompleteHeader() {
        WaitUtils.waitForElementToBeVisible(driver, completeHeader);
        return Commons.getText(completeHeader);
    }

    @Step("Get completion text")
    public String getCompleteText() {
        WaitUtils.waitForElementToBeVisible(driver, completeText);
        return Commons.getText(completeText);
    }

    @Step("Go back to products")
    public ProductsPage backToProducts() {
        WaitUtils.waitForElementToBeClickable(driver, backToProductsButton);
        Commons.click(backToProductsButton);
        return new ProductsPage(driver);
    }

    @Step("Check if on checkout step one")
    public boolean isOnCheckoutStepOne() {
        return Commons.isDisplayed(firstNameField) &&
               Commons.isDisplayed(lastNameField) &&
               Commons.isDisplayed(postalCodeField);
    }

    @Step("Check if on checkout step two")
    public boolean isOnCheckoutStepTwo() {
        return Commons.isDisplayed(finishButton) &&
               Commons.isDisplayed(subtotalLabel);
    }

    @Step("Check if order is complete")
    public boolean isOrderComplete() {
        return Commons.isDisplayed(completeHeader) &&
               Commons.isDisplayed(backToProductsButton);
    }
}
