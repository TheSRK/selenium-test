package com.saucedemo.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils {
    private static final int DEFAULT_TIMEOUT = 10;

    public static void waitForElementToBeVisible(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementToBeVisible(WebDriver driver, WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForElementToBeClickable(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementToBeClickable(WebDriver driver, WebElement element, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForAllElementsToBeVisible(WebDriver driver, List<WebElement> elements) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public static void waitForUrlContains(WebDriver driver, String keyword) {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT))
                .until(ExpectedConditions.urlContains(keyword));
    }

    public static void waitForUrlContains(WebDriver driver, String keyword, int timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))
                .until(ExpectedConditions.urlContains(keyword));
    }

    public static boolean isElementDisplayed(WebDriver driver, WebElement element) {
        try {
            waitForElementToBeVisible(driver, element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
