package com.saucedemo.utils;

import com.saucedemo.factory.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        // Code to take screenshot
        TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getDriver();
        File screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);

        String time = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss-aa").format(new Date());
        String destinationPath = "src/test/resources/screenshots/" + time + ".png";
        File destinationFile = new File(destinationPath);

        // Create screenshots directory if it doesn't exist
        destinationFile.getParentFile().mkdirs();

        try {
            FileUtils.copyFile(screenshotFile, destinationFile);
            System.out.println("Screenshot saved at: " + destinationPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
