package com.appium.facebook.androidUIselector;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;

public class FacebookLoginTest {
	
	@Test
	public void testLoginFB() throws InterruptedException, MalformedURLException {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		//desiredCapabilities.setCapability("automationName", "Appium");
		desiredCapabilities.setCapability("automationName", "Selendroid");
		
		desiredCapabilities.setCapability("platformVersion", "4.0.3");
		desiredCapabilities.setCapability("deviceName", "GT-I9100G");
		
		desiredCapabilities.setCapability("platformName", "Android");
		
		//desiredCapabilities.setCapability("platformVersion", "6.0");
		//desiredCapabilities.setCapability("deviceName", "Android Emulator");
		
		desiredCapabilities.setCapability("app", "D:\\Personal\\Facebook-29.0.0.23.13.apk");
		desiredCapabilities.setCapability("appPackage", "com.facebook.katana");
		desiredCapabilities.setCapability("appActivity", "com.facebook.katana.LoginActivity");
		desiredCapabilities.setCapability("noReset","true");
		desiredCapabilities.setCapability("fullReset","false");
		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
		
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		//Thread.sleep(20000);
		
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/login_username\")")
				.sendKeys("adysept@gmail.com");
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/login_password\")")
				.sendKeys("YOUmatter");
		driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.facebook.katana:id/login_login\")"
				+ ".text(\"LOG IN\")").click();
		
		Thread.sleep(10000);
		
		
		
	}
}
