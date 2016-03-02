package com.appium.calculator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.TouchAction;
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
	DesiredCapabilities desiredCapabilities;
	AndroidDriver driver;
	Workbook workbook;
	Sheet sheet;
	WritableWorkbook writeBookA;
	WritableSheet sheetA;
	String num1, num2, op;
	int i, index;
	
	@Before
	public void read() throws BiffException, IOException, RowsExceededException, WriteException, InterruptedException{
		desiredCapabilities = new DesiredCapabilities();
		desiredCapabilities.setCapability("automationName", "Appium");
		//desiredCapabilities.setCapability("automationName", "Selendroid");
		desiredCapabilities.setCapability("platformName", "Android");
		
		desiredCapabilities.setCapability("platformVersion", "6.0");
		desiredCapabilities.setCapability("deviceName", "Android Emulator");
		
		desiredCapabilities.setCapability("appPackage", "com.android.calculator2");
		desiredCapabilities.setCapability("appActivity", "Calculator");
		desiredCapabilities.setCapability("noReset","true");
		desiredCapabilities.setCapability("fullReset","false");
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		workbook = Workbook.getWorkbook(new File("calc.xls"));
		sheet = workbook.getSheet(0);
		writeBookA = Workbook.createWorkbook(new File("calcAns.xls"));
		sheetA = writeBookA.createSheet("First Sheet", 0);
	}
	
	@Test
	public void test() throws BiffException, IOException, RowsExceededException, WriteException, InterruptedException {
		for(i=0; i<sheet.getRows(); i++ ){
			Cell n1 = sheet.getCell(0,i); 
			Cell n2 = sheet.getCell(1,i);//(col,row)
			Cell oprtn = sheet.getCell(2,i);
			num1 = n1.getContents();
			num2 = n2.getContents();
			op = oprtn.getContents();
		
			for(index=0; index<num1.length() ; index++ ){
				String digit = "com.android.calculator2:id/digit_" + num1.charAt(index);
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+ digit +"\")").click();
			}
			
			if(op.equals("1")){
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.android.calculator2:id/op_add\")").click();
			} else if(op.equals("2")){
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.android.calculator2:id/op_sub\")").click();
			} else if(op.equals("3")){
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.android.calculator2:id/op_mul\")").click();
			} else if(op.equals("4")){
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.android.calculator2:id/op_div\")").click();;
			}
			
			for(index=0; index<num2.length() ; index++ ){
				String digit = "com.android.calculator2:id/digit_" + num2.charAt(index);
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\""+ digit +"\")").click();
			}
			
			String ans = driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.android.calculator2:id/result\")").getText(); 
			
			//System.out.println("Success" + num1 +" " +num2 +" " +op+" " +ans);
			Label labelN1 = new Label(0, i, num1);
			sheetA.addCell(labelN1);
			Label labelN2 = new Label(1, i, num2);
			sheetA.addCell(labelN2);
			Label labelOp = new Label(2, i, op);
			sheetA.addCell(labelOp);
			Label labelAns = new Label(3, i, ans);
			sheetA.addCell(labelAns);
			
			WebElement del = driver.findElementById("com.android.calculator2:id/del");
			TouchAction action = new TouchAction(driver);
			action.longPress(del).release().perform();
			
		}
	}
	
	@After
	public void output() throws IOException, WriteException{
		writeBookA.write(); 
		writeBookA.close();
		workbook.close();
	}

	public boolean checkString(String a){
		
		for(int i=0 ;i<a.length() ; i++ ){
			if(Character.isAlphabetic(a.charAt(i))){
				return false;
			}
		}
		return true;
	}
}
