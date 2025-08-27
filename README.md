# SauceDemo Test Automation Framework

Selenium WebDriver automation framework for SauceDemo using Java, TestNG, and Allure reporting.

## Project Structure

```
src/test/java/com/saucedemo/
├── pages/          # Page Object classes
├── tests/          # Test classes
├── utils/          # Utility classes
├── factory/        # Browser and driver management
└── base/           # Base test class

src/test/resources/
├── config.properties   # Configuration settings
├── testng.xml         # TestNG suite configuration
└── testdata/          # Test data files
```

## How to Run

### Run tests
```bash
mvn clean test
```

### Generate Allure report
```bash
mvn allure:serve
```

### Run with different browser
```bash
mvn test -Dbrowser=firefox
```

### Run in headless mode
```bash
mvn test -Dheadless=true
```

## Configuration

Edit `src/test/resources/config.properties`:
```properties
base.url=https://www.saucedemo.com/
browser=chrome
headless=false
implicit.wait=10
```
