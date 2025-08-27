package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.utils.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartPageTests extends BaseTest {

    private CartPage cartPage;

    @BeforeMethod
    public void setupCartWithProduct() {
        LoginPage loginPage = new LoginPage(getDriver());
        ProductsPage productsPage = loginPage.login("standard_user", "secret_sauce");
        productsPage.addFirstProductToCart();
        cartPage = productsPage.clickCart();
    }

    @Test(priority = 1)
    @Description("Verify cart page loads with correct title and items")
    @Severity(SeverityLevel.CRITICAL)
    public void verify_cart_page_loaded() {
        Assert.assertTrue(cartPage.isOnCartPage());
        Assert.assertEquals(cartPage.getPageTitle(), Constants.CART_PAGE_TITLE);
        Assert.assertEquals(cartPage.getCartItemsCount(), 1);
        System.out.println("Cart page loaded with 1 item");
    }

    @Test(priority = 2)
    @Description("Verify user can remove item from cart")
    @Severity(SeverityLevel.NORMAL)
    public void remove_item_from_cart() {
        cartPage.removeItemFromCart(0);
        Assert.assertTrue(cartPage.isCartEmpty());
        System.out.println("Item removed from cart successfully");
    }

    @Test(priority = 3)
    @Description("Verify user can continue shopping")
    @Severity(SeverityLevel.NORMAL)
    public void continue_shopping() {
        ProductsPage productsPage = cartPage.continueShopping();
        Assert.assertTrue(productsPage.isOnProductsPage());
        System.out.println("Continue shopping successful");
    }

    @Test(priority = 4)
    @Description("Verify user can proceed to checkout")
    @Severity(SeverityLevel.BLOCKER)
    public void proceed_to_checkout() {
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        Assert.assertTrue(checkoutPage.isOnCheckoutStepOne());
        System.out.println("Proceeded to checkout successfully");
    }
}
