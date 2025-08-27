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
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserJourneyTests extends BaseTest {

    @Test(dataProvider = "checkoutData", dataProviderClass = DataProviders.class)
    @Description("Complete user journey: Login -> Select Product -> Checkout -> Payment -> Logout")
    @Severity(SeverityLevel.CRITICAL)
    public void complete_user_journey(String firstName, String lastName, String postalCode) {
        // Step 1: User Login
        LoginPage loginPage = loginToApplication();

        // Step 2: Select a Product
        ProductsPage productsPage = selectProduct(loginPage);

        // Step 3: Go to Cart and Checkout
        CartPage cartPage = goToCart(productsPage);

        // Step 4: Complete Checkout Process (Payment)
        CheckoutPage checkoutPage = completeCheckout(cartPage, firstName, lastName, postalCode);

        // Step 5: Verify Order Completion
        verifyOrderCompletion(checkoutPage);

        // Step 6: Go back to products and Logout
        ProductsPage finalProductsPage = returnToProducts(checkoutPage);
        logoutUser(finalProductsPage);

        System.out.println("✅ Complete user journey completed successfully for: " + firstName + " " + lastName);
    }

    @Step("Step 1: Login to application")
    private LoginPage loginToApplication() {
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        System.out.println("Step 1: User is on login page");
        return loginPage;
    }

    @Step("Step 2: Select product and add to cart")
    private ProductsPage selectProduct(LoginPage loginPage) {
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(productsPage.isOnProductsPage(), "Should be on products page after login");

        // Add first product to cart
        productsPage.addFirstProductToCart();
        Assert.assertEquals(productsPage.getCartItemCount(), "1", "Cart should have 1 item");

        System.out.println("Step 2: Product added to cart successfully");
        return productsPage;
    }

    @Step("Step 3: Navigate to cart")
    private CartPage goToCart(ProductsPage productsPage) {
        CartPage cartPage = productsPage.clickCart();
        Assert.assertTrue(cartPage.isOnCartPage(), "Should be on cart page");
        Assert.assertEquals(cartPage.getCartItemsCount(), 1, "Cart should contain 1 item");

        System.out.println("Step 3: User navigated to cart page");
        return cartPage;
    }

    @Step("Step 4: Complete checkout process with payment information")
    private CheckoutPage completeCheckout(CartPage cartPage, String firstName, String lastName, String postalCode) {
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        Assert.assertTrue(checkoutPage.isOnCheckoutStepOne(), "Should be on checkout step 1");

        // Fill checkout information (this represents payment/billing info)
        checkoutPage.fillCheckoutInformation(firstName, lastName, postalCode);
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isOnCheckoutStepTwo(), "Should be on checkout step 2 (payment review)");
        System.out.println("Step 4a: Payment information entered successfully");

        // Complete the payment
        checkoutPage.clickFinish();

        System.out.println("Step 4b: Payment processed successfully");
        return checkoutPage;
    }

    @Step("Step 5: Verify order completion")
    private void verifyOrderCompletion(CheckoutPage checkoutPage) {
        Assert.assertTrue(checkoutPage.isOrderComplete(), "Order should be completed");
        Assert.assertTrue(checkoutPage.getCompleteHeader().contains("Thank you"),
                         "Should display thank you message");

        System.out.println("Step 5: Order completed successfully - Thank you message displayed");
    }

    @Step("Step 6a: Return to products page")
    private ProductsPage returnToProducts(CheckoutPage checkoutPage) {
        ProductsPage productsPage = checkoutPage.backToProducts();
        Assert.assertTrue(productsPage.isOnProductsPage(), "Should be back on products page");

        System.out.println("Step 6a: Returned to products page");
        return productsPage;
    }

    @Step("Step 6b: Logout from application")
    private void logoutUser(ProductsPage productsPage) {
        LoginPage loginPage = productsPage.logout();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should be back on login page after logout");

        System.out.println("Step 6b: User logged out successfully");
    }
}
