package com.appium.calender;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;


public class AutomateCalender {

	@Test
	public void testCalender() throws InterruptedException, MalformedURLException{
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		//desiredCapabilities.setCapability("automationName", "Appium");
		desiredCapabilities.setCapability("automationName", "Selendroid");
		
		desiredCapabilities.setCapability("platformVersion", "4.1.2");
		desiredCapabilities.setCapability("deviceName", "GT-I9300");
		
		desiredCapabilities.setCapability("platformName", "Android");
		
		//desiredCapabilities.setCapability("platformVersion", "6.0");
		//desiredCapabilities.setCapability("deviceName", "Android Emulator");
		
		desiredCapabilities.setCapability("app", "D:\\Personal\\SimpleCalc.apk");
		
		desiredCapabilities.setCapability("appPackage", "com.example.mukesh.simple_calculator");
		desiredCapabilities.setCapability("appActivity", "com.example.mukesh.simple_calculator.MainActivity");
		desiredCapabilities.setCapability("noReset","true");
		desiredCapabilities.setCapability("fullReset","false");
		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		//Thread.sleep(20000);
		
		Thread.sleep(10000);
		
	}
}
