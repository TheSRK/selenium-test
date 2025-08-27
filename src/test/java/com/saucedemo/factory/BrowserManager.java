package com.saucedemo.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class BrowserManager {

    public static WebDriver createBrowserDriver(String browserType) {
        // Priority: parameter > system property > default
        String browser = browserType != null ? browserType : System.getProperty("browser", "chrome");
        browser = browser.toLowerCase();

        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        System.out.println("BrowserManager: Creating driver for browser: " + browser);
        System.out.println("BrowserManager: Headless mode: " + isHeadless);

        try {
            switch (browser) {
                case "firefox":
                    System.out.println("Attempting to create Firefox driver...");
                    return new FirefoxDriver(getFirefoxOptions(isHeadless));
                case "chrome":
                default:
                    System.out.println("Attempting to create Chrome driver...");
                    return new ChromeDriver(getChromeOptions(isHeadless));
            }
        } catch (Exception e) {
            System.out.println("Failed to create " + browser + " driver: " + e.getMessage());
            System.out.println("Falling back to Chrome driver...");
            return new ChromeDriver(getChromeOptions(isHeadless));
        }
    }

    private static ChromeOptions getChromeOptions(boolean isHeadless) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--incognito");

        if (isHeadless) {
            chromeOptions.addArguments("--headless=new");
            chromeOptions.addArguments("--window-size=1920,1080");
        }
        return chromeOptions;
    }

    private static FirefoxOptions getFirefoxOptions(boolean isHeadless) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();

        // Prevent "restore previous session" + default-browser nag
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.sessionstore.resume_from_crash", false);
        profile.setPreference("browser.shell.checkDefaultBrowser", false);
        profile.setPreference("browser.startup.page", 0);
        profile.setPreference("browser.sessionstore.resume_session_once", false);
        profile.setPreference("browser.warnOnQuit", false);
        profile.setPreference("browser.tabs.warnOnClose", false);
        profile.setPreference("browser.sessionstore.restore_on_demand", false);
        profile.setPreference("browser.sessionstore.max_resumed_crashes", 0);

        // Apply the profile to Firefox options
        firefoxOptions.setProfile(profile);
        firefoxOptions.addArguments("--private");

        if (isHeadless) {
            firefoxOptions.addArguments("--headless");
            firefoxOptions.addArguments("--window-size=1920,1080");
        }

        return firefoxOptions;
    }
}