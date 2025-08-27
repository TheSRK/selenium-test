package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.utils.DataProviders;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutPageTests extends BaseTest {

    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setupCheckout() {
        LoginPage loginPage = new LoginPage(getDriver());
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");
        productsPage.addFirstProductToCart();
        CartPage cartPage = productsPage.clickCart();
        checkoutPage = cartPage.proceedToCheckout();
    }

    @Test(priority = 1)
    @Description("Verify checkout step one loads correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void verify_checkout_step_one_loaded() {
        Assert.assertTrue(checkoutPage.isOnCheckoutStepOne());
        System.out.println("Checkout step one loaded successfully");
    }

    @Test(priority = 2, dataProvider = "checkoutData", dataProviderClass = DataProviders.class)
    @Description("Verify user can complete checkout with valid information")
    @Severity(SeverityLevel.BLOCKER)
    public void complete_checkout_with_valid_info(String firstName, String lastName, String postalCode) {
        checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isOnCheckoutStepTwo());
        checkoutPage.clickFinish();

        Assert.assertTrue(checkoutPage.isOrderComplete());
        Assert.assertTrue(checkoutPage.getCompleteHeader().contains("Thank you"));
        System.out.println("Checkout completed successfully with: " + firstName + " " + lastName);
    }

    @Test(priority = 3, dataProvider = "invalidCheckoutData", dataProviderClass = DataProviders.class)
    @Description("Verify checkout fails with empty required fields")
    @Severity(SeverityLevel.NORMAL)
    public void checkout_fails_with_empty_fields(String firstName, String lastName, String postalCode, String expectedError) {
        checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
        checkoutPage.clickContinue();
        Assert.assertTrue(checkoutPage.getErrorMessage().contains("required"));
        System.out.println("Validation working for: " + expectedError);
    }

    @Test(priority = 4)
    @Description("Verify user can cancel checkout and return to cart")
    @Severity(SeverityLevel.NORMAL)
    public void cancel_checkout() {
        CartPage cartPage = checkoutPage.clickCancel();
        Assert.assertTrue(cartPage.isOnCartPage());
        System.out.println("Checkout cancelled successfully");
    }
}
