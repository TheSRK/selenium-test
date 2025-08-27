package com.saucedemo.base;

import com.saucedemo.factory.BrowserManager;
import com.saucedemo.factory.DriverManager;
import com.saucedemo.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import java.time.Duration;

public class BaseTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser){
        System.out.println("TestNG Parameter received: " + browser);

        WebDriver driver = BrowserManager.createBrowserDriver(browser);
        DriverManager.setDriver(driver);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getImplicitWait()));
        getDriver().get(ConfigReader.getBaseUrl());

        System.out.println("Test started with browser: " + browser);
    }

    protected WebDriver getDriver(){
        return DriverManager.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(){
        System.out.println("tearDown() method called - cleaning up driver");
        DriverManager.quitDriver();
    }

}