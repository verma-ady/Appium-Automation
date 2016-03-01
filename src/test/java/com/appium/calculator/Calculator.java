package com.appium.calculator;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Calculator {

	@Test
	public void test() throws BiffException, IOException, RowsExceededException, WriteException, InterruptedException {
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("automationName", "Appium");
		//desiredCapabilities.setCapability("automationName", "Selendroid");
		desiredCapabilities.setCapability("platformName", "Android");
		
		desiredCapabilities.setCapability("platformVersion", "6.0");
		desiredCapabilities.setCapability("deviceName", "Android Emulator");
		
		desiredCapabilities.setCapability("app", "D:\\Aditya\\Android\\Calculator\\app\\build\\outputs\\apk\\app-debug.apk");
		desiredCapabilities.setCapability("appPackage", "com.calculator");
		desiredCapabilities.setCapability("appActivity", "Launch");
		desiredCapabilities.setCapability("noReset","true");
		desiredCapabilities.setCapability("fullReset","false");
		AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		Workbook workbook = Workbook.getWorkbook(new File("calc.xls"));
		Sheet sheet = workbook.getSheet(0);
		WritableWorkbook writeBook = Workbook.createWorkbook(new File("calc.xls"), workbook);
		WritableSheet sheet1 = writeBook.getSheet(0); 
		
		for(int i=0; i<sheet1.getRows() ; i++ ){
			Cell num1 = sheet.getCell(0,i); 
			Cell num2 = sheet.getCell(1,i);//(col,row)
			Cell op = sheet.getCell(2,i);
			driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/num1\")")
			.sendKeys(num1.getContents());
			driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/num2\")")
			.sendKeys(num2.getContents());
			
			driver.navigate().back();
			if(op.getContents().equals("1")){
				System.out.println(op.getContents());
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/add\")").click();
			} else if(op.getContents().equals("2")){
				System.out.println(op.getContents());
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/sub\")").click();
			} else if(op.getContents().equals("3")){
				System.out.println(op.getContents());
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/mul\")").click();
			} else if(op.getContents().equals("4")){
				System.out.println(op.getContents());
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/div\")").click();;
			}
			String ans = driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/ans\")").getText(); 
			Label label = new Label(3, i, ans);
			sheet1.addCell(label);
			if(!ans.contains("^[0-9]*$")){
				fail(ans);
			}
			//Thread.sleep(2000);
			driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/num1\")")
			.sendKeys("");
			driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/num2\")")
			.sendKeys("");
		}
		writeBook.write(); 
		writeBook.close();
		workbook.close();
	}

}
