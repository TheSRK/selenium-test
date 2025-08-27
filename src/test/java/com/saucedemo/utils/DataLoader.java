package com.saucedemo.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode loadJsonData(String fileName) {
        try {
            InputStream inputStream = DataLoader.class.getClassLoader()
                    .getResourceAsStream("testdata/" + fileName);
            if (inputStream == null) {
                throw new RuntimeException("File not found: testdata/" + fileName);
            }
            return objectMapper.readTree(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON data from: " + fileName, e);
        }
    }

    public static List<Object[]> getLoginTestData(String fileName, String dataType) {
        JsonNode rootNode = loadJsonData(fileName);
        JsonNode dataNode = rootNode.get(dataType);

        List<Object[]> testData = new ArrayList<>();

        if (dataNode != null && dataNode.isArray()) {
            for (JsonNode node : dataNode) {
                String testCase = node.get("testCase").asText();
                String username = node.get("username").asText();
                String password = node.get("password").asText();
                String expectedResult = node.get("expectedResult").asText();

                if ("success".equals(expectedResult)) {
                    String expectedPage = node.get("expectedPage").asText();
                    testData.add(new Object[]{testCase, username, password, expectedResult, expectedPage});
                } else {
                    String expectedError = node.get("expectedError").asText();
                    testData.add(new Object[]{testCase, username, password, expectedResult, expectedError});
                }
            }
        }

        return testData;
    }
}
