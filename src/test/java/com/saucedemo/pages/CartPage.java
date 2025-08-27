package com.saucedemo.pages;

import com.saucedemo.utils.Commons;
import com.saucedemo.utils.Constants;
import com.saucedemo.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CartPage {
    private WebDriver driver;

    // Page Factory Elements
    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> cartItemPrices;

    @FindBy(css = ".cart_button")
    private List<WebElement> removeButtons;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    // Constructor
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Get cart page title")
    public String getPageTitle() {
        WaitUtils.waitForElementToBeVisible(driver, pageTitle);
        return Commons.getText(pageTitle);
    }

    @Step("Check if on cart page")
    public boolean isOnCartPage() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, pageTitle);
            return driver.getCurrentUrl().contains("/cart.html") &&
                   Commons.getText(pageTitle).equals(Constants.CART_PAGE_TITLE);
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Get cart items count")
    public int getCartItemsCount() {
        WaitUtils.waitForAllElementsToBeVisible(driver, cartItems);
        return cartItems.size();
    }

    @Step("Remove item from cart by index: {index}")
    public CartPage removeItemFromCart(int index) {
        if (index >= 0 && index < removeButtons.size()) {
            WaitUtils.waitForElementToBeClickable(driver, removeButtons.get(index));
            Commons.click(removeButtons.get(index));
        }
        return this;
    }

    @Step("Continue shopping")
    public ProductsPage continueShopping() {
        WaitUtils.waitForElementToBeClickable(driver, continueShoppingButton);
        Commons.click(continueShoppingButton);
        return new ProductsPage(driver);
    }

    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        WaitUtils.waitForElementToBeClickable(driver, checkoutButton);
        Commons.click(checkoutButton);
        return new CheckoutPage(driver);
    }

    @Step("Check if cart is empty")
    public boolean isCartEmpty() {
        try {
            return cartItems.isEmpty();
        } catch (Exception e) {
            return true;
        }
    }

    @Step("Get cart badge count")
    public String getCartBadgeCount() {
        try {
            return Commons.getText(cartBadge);
        } catch (Exception e) {
            return "0";
        }
    }
}
