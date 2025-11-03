package com.fdmgroup.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * Updated DriverUtilities for Jenkins and local runs.
 * - Uses WebDriverManager (no manual driver setup)
 * - Supports headless mode via -Dheadless=true
 * - Thread-safe Singleton (one driver per thread)
 */
public class DriverUtilities {

    private static DriverUtilities instance;
    private WebDriver driver;

    private DriverUtilities() {}

    // Singleton accessor
    public static synchronized DriverUtilities getInstance() {
        if (instance == null) {
            instance = new DriverUtilities();
        }
        return instance;
    }

    // Returns the active WebDriver, creating it if needed
    public synchronized WebDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }

    // Closes and resets driver (used in Hooks @After)
    public static synchronized void resetDriver() {
        if (instance != null && instance.driver != null) {
            instance.driver.quit();
            instance.driver = null;
        }
        instance = null;
    }

    // create browser based on config.properties
    private void createDriver() {
        String browser = getBrowserName();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));

        switch (browser.toLowerCase()) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=1536,960", "--no-sandbox", "--disable-gpu");
                driver = new ChromeDriver(chromeOptions);
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("-headless");
                driver = new FirefoxDriver(firefoxOptions);
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) edgeOptions.addArguments("--headless=new");
                driver = new EdgeDriver(edgeOptions);
            }
            default -> throw new IllegalArgumentException("❌ Unknown browser: " + browser);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    // Reads browser from config.properties (default Chrome)
    private String getBrowserName() {
        Properties config = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/config.properties")) {
            config.load(fis);
        } catch (IOException e) {
            System.err.println("⚠️ Could not load config.properties, using default Chrome");
        }
        return config.getProperty("browser", "Chrome").trim();
    }
}
