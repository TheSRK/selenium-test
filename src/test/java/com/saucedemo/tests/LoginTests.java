package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.Constants;
import com.saucedemo.utils.DataProviders;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    @Test(priority = 1)
    @Description("Verify login page loads correctly with all elements visible")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyLoginPageLoaded() {
        LoginPage loginPage = new LoginPage(getDriver());
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page should be displayed");
        System.out.println("Login page is loaded successfully");
    }

    @Test(dataProvider = "validUsers", dataProviderClass = DataProviders.class, priority = 2)
    @Description("Verify login succeeds for valid users")
    @Severity(SeverityLevel.BLOCKER)
    public void login_succeeds_for_valid_users(String user, String pass) {
        LoginPage loginPage = new LoginPage(getDriver());
        ProductsPage productsPage = loginPage.login(user, pass);
        Assert.assertTrue(productsPage.isOnProductsPage());
        Assert.assertEquals(productsPage.getPageTitle(), Constants.PRODUCTS_PAGE_TITLE);
        System.out.println("Valid loginPage test passed for user: " + user);
    }

    @Test(dataProvider = "invalidUsers", dataProviderClass = DataProviders.class, priority = 3)
    @Description("Verify login fails for invalid inputs")
    @Severity(SeverityLevel.CRITICAL)
    public void login_fails_for_invalid_inputs(String user, String pass, String expectedMsg) {
        var loginPage = new LoginPage(getDriver());
        loginPage.enterUsername(user).enterPassword(pass).clickLoginButton();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        Assert.assertTrue(loginPage.getErrorMessage().contains(expectedMsg));
        System.out.println("Invalid loginPage test passed for user: " + user);
    }
}
