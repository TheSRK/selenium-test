package com.saucedemo.pages;

import com.saucedemo.utils.Commons;
import com.saucedemo.utils.WaitUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsPage {
    private WebDriver driver;

    // Page Factory Elements
    @FindBy(css = ".title")
    private WebElement pageTitle;

    @FindBy(css = ".inventory_item")
    private List<WebElement> productItems;

    @FindBy(css = ".inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(css = ".inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(css = ".btn_inventory")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = ".product_sort_container")
    private WebElement sortDropdown;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    // Constructor
    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Get page title")
    public String getPageTitle() {
        WaitUtils.waitForElementToBeVisible(driver, pageTitle);
        return Commons.getText(pageTitle);
    }

    @Step("Check if on products page")
    public boolean isOnProductsPage() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, pageTitle);
            return driver.getCurrentUrl().contains("/inventory.html") &&
                   Commons.getText(pageTitle).equals("Products");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Add product to cart by name: {productName}")
    public ProductsPage addProductToCart(String productName) {
        try {
            WebElement addButton = driver.findElement(
                By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button[contains(@id,'add-to-cart')]")
            );
            WaitUtils.waitForElementToBeClickable(driver, addButton);
            Commons.click(addButton);
        } catch (Exception e) {
            // Product not found or other error
        }
        return this;
    }

    @Step("Add first product to cart")
    public ProductsPage addFirstProductToCart() {
        if (!addToCartButtons.isEmpty()) {
            WaitUtils.waitForElementToBeClickable(driver, addToCartButtons.get(0));
            Commons.click(addToCartButtons.get(0));
        }
        return this;
    }

    @Step("Get cart item count")
    public String getCartItemCount() {
        try {
            WaitUtils.waitForElementToBeVisible(driver, cartBadge);
            return Commons.getText(cartBadge);
        } catch (Exception e) {
            return "0";
        }
    }

    @Step("Click cart icon")
    public CartPage clickCart() {
        WaitUtils.waitForElementToBeClickable(driver, cartIcon);
        Commons.click(cartIcon);
        return new CartPage(driver);
    }

    @Step("Get all product names")
    public List<String> getAllProductNames() {
        WaitUtils.waitForAllElementsToBeVisible(driver, productNames);
        return productNames.stream()
            .map(Commons::getText)
            .collect(Collectors.toList());
    }

    @Step("Get all product prices")
    public List<String> getAllProductPrices() {
        WaitUtils.waitForAllElementsToBeVisible(driver, productPrices);
        return productPrices.stream()
            .map(Commons::getText)
            .collect(Collectors.toList());
    }

    @Step("Sort products by: {sortOption}")
    public ProductsPage sortProducts(String sortOption) {
        WaitUtils.waitForElementToBeClickable(driver, sortDropdown);
        Select select = new Select(sortDropdown);
        select.selectByVisibleText(sortOption);
        return this;
    }

    @Step("Get product count")
    public int getProductCount() {
        WaitUtils.waitForAllElementsToBeVisible(driver, productItems);
        return productItems.size();
    }

    @Step("Open menu")
    public ProductsPage openMenu() {
        WaitUtils.waitForElementToBeClickable(driver, menuButton);
        Commons.click(menuButton);
        return this;
    }

    @Step("Logout")
    public LoginPage logout() {
        openMenu();
        WaitUtils.waitForElementToBeClickable(driver, logoutLink);
        Commons.click(logoutLink);
        return new LoginPage(driver);
    }
}
