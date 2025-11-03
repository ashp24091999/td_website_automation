package com.fdmgroup.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverUtilities_old {
	// 1 Private Static Instance 
		private static DriverUtilities_old driverUtilities;
		private WebDriver driver;
		
		// 2 Private Constructor 
		private DriverUtilities_old() {
			super();
		}
		
		// 3 Public static getInstance()
		public static DriverUtilities_old getInstance() {
			if(driverUtilities == null) {
				driverUtilities = new DriverUtilities_old();
			}
			return driverUtilities;
		}
		
		public static void resetDriver() {
			driverUtilities.driver.quit();
			driverUtilities.driver = null;
			driverUtilities = null;
		}
		
		public WebDriver getDriver() {
			if(driver == null) {
				createDriver();
			}
			return driver;
		}
		
		private void createDriver() {
			String driverName = getDriverName();
			switch(driverName) {
			case "Chrome":
				this.driver = new ChromeDriver();
				break;
			case "Firefox":
				this.driver = new FirefoxDriver();
				break;
			case "Edge":
				this.driver = new EdgeDriver();
				break;
			default:
				break;
			}
		}
		
		private String getDriverName() {
			String driverName = "";
			Properties config = new Properties();
			try {
				config.load(new FileInputStream("src/test/resources/config.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			driverName = config.getProperty("browser");
			return driverName;
		}

}
