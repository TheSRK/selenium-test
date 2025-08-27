package com.saucedemo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.List;

public class DataProviders {
    private static final Faker faker = new Faker();
    private static final String LOGIN_DATA_FILE = "login-data.json";

    @DataProvider(name = "validUsers")
    public static Object[][] getValidUsers() {
        try {
            JsonNode rootNode = DataLoader.loadJsonData(LOGIN_DATA_FILE);
            JsonNode validUsers = rootNode.get("validUsers");

            List<Object[]> testData = new ArrayList<>();
            for (JsonNode userArray : validUsers) {
                String username = userArray.get(0).asText();
                String password = userArray.get(1).asText();
                testData.add(new Object[]{username, password});
            }
            return testData.toArray(new Object[0][]);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load valid users data", e);
        }
    }

    @DataProvider(name = "invalidUsers")
    public static Object[][] getInvalidUsers() {
        try {
            JsonNode rootNode = DataLoader.loadJsonData(LOGIN_DATA_FILE);
            JsonNode invalidUsers = rootNode.get("invalidUsers");

            List<Object[]> testData = new ArrayList<>();
            for (JsonNode userArray : invalidUsers) {
                String username = userArray.get(0).asText();
                String password = userArray.get(1).asText();
                String expectedMsg = userArray.get(2).asText();
                testData.add(new Object[]{username, password, expectedMsg});
            }
            return testData.toArray(new Object[0][]);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load invalid users data", e);
        }
    }

    @DataProvider(name = "checkoutData")
    public static Object[][] getCheckoutData() {
        return new Object[][]{

                {faker.name().firstName(), faker.name().lastName(), faker.address().zipCode()}
        };
    }

    @DataProvider(name = "invalidCheckoutData")
    public static Object[][] getInvalidCheckoutData() {
        return new Object[][]{
                {"", "LastName", "12345", "Error: First Name is required"},
                {"FirstName", "", "12345", "Error: Last Name is required"},
                {"FirstName", "LastName", "", "Error: Postal Code is required"},
                {"", "", "", "Error: First Name is required"}
        };
    }
}
