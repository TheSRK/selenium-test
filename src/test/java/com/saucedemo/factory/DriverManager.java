package com.saucedemo.factory;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    private static ThreadLocal<WebDriver> threadLocal = new ThreadLocal<>();

    public static void setDriver(WebDriver driverRef){
        threadLocal.set(driverRef);
    }

    public static WebDriver getDriver(){
        return threadLocal.get();
    }

    public static void quitDriver(){
        WebDriver driver = getDriver();
        if(driver != null){
            try {
                driver.quit();
            } catch (Exception e) {
                // Log any unexpected errors but don't fail the test
                System.out.println("Error during driver cleanup: " + e.getMessage());
            } finally {
                threadLocal.remove();
            }
        }
    }
}