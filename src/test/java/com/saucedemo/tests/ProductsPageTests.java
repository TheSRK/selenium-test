package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.pages.CartPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductsPageTests extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage(getDriver());
        productsPage = loginPage.login("standard_user", "secret_sauce");
    }

    @Test(priority = 1)
    @Description("Verify products page loads with correct title and elements")
    @Severity(SeverityLevel.CRITICAL)
    public void verify_products_page_loaded() {
        Assert.assertTrue(productsPage.isOnProductsPage());
        Assert.assertEquals(productsPage.getPageTitle(), "Products");
        Assert.assertTrue(productsPage.getProductCount() > 0);
        System.out.println("Products page loaded successfully with " + productsPage.getProductCount() + " products");
    }

    @Test(priority = 2)
    @Description("Verify user can add product to cart")
    @Severity(SeverityLevel.BLOCKER)
    public void add_product_to_cart() {
        productsPage.addFirstProductToCart();
        Assert.assertEquals(productsPage.getCartItemCount(), "1");
        System.out.println("Product added to cart successfully");
    }

    @Test(priority = 3)
    @Description("Verify user can navigate to cart")
    @Severity(SeverityLevel.NORMAL)
    public void navigate_to_cart() {
        productsPage.addFirstProductToCart();
        CartPage cartPage = productsPage.clickCart();
        Assert.assertTrue(cartPage.isOnCartPage());
        System.out.println("Navigation to cart successful");
    }

    @Test(priority = 4)
    @Description("Verify user can logout")
    @Severity(SeverityLevel.NORMAL)
    public void logout_from_products_page() {
        LoginPage loginPage = productsPage.logout();
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
        System.out.println("Logout successful");
    }
}
