package com.saucedemo.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.openqa.selenium.Keys.ENTER;

public class Commons {

    public static void click(WebElement element) {
        element.click();
    }

    public static String getText(WebElement element) {
        return element.getText();
    }

    public static void sendKeys(WebElement element, String inputString) {
        element.clear();
        element.sendKeys(inputString);
    }

    public static void sendKeysEnter(WebElement element, String inputString) {
        element.clear();
        element.sendKeys(inputString);
        element.sendKeys(ENTER);
    }

    public static String getTitle(WebDriver driver) {
        return driver.getTitle();
    }

    public static boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEnabled(WebElement element) {
        try {
            return element.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (Exception e) {
            return false;
        }
    }

    public static void clear(WebElement element) {
        element.clear();
    }

    public static void jsClick(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public static void jsScrollIntoView(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
