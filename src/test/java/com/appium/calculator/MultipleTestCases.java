package com.appium.calculator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
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

public class MultipleTestCases {
	DesiredCapabilities desiredCapabilities;
	AndroidDriver driver;
	Workbook workbook;
	Sheet sheet;
	WritableWorkbook writeBookS, writeBookF;
	WritableSheet sheetF, sheetS;
	String num1, num2, op;
	int i, iF, iS;
	
	@Before
	public void read() throws BiffException, IOException, RowsExceededException, WriteException, InterruptedException{
		desiredCapabilities = new DesiredCapabilities();
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
		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),desiredCapabilities);
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		
		workbook = Workbook.getWorkbook(new File("calc.xls"));
		sheet = workbook.getSheet(0);
		writeBookF = Workbook.createWorkbook(new File("fail.xls"));
		writeBookS = Workbook.createWorkbook(new File("success.xls"));
		sheetF = writeBookF.createSheet("First Sheet", 0);
		sheetS = writeBookS.createSheet("First Sheet", 0);
		iS = iF = 0;
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
		
			driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/num1\")")
			.sendKeys(num1);
			driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/num2\")")
			.sendKeys(num2);
		
			try{
				driver.hideKeyboard();
			} catch(org.openqa.selenium.WebDriverException E){
				
			}
			if(op.equals("1")){
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/add\")").click();
			} else if(op.equals("2")){
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/sub\")").click();
			} else if(op.equals("3")){
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/mul\")").click();
			} else if(op.equals("4")){
				driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/div\")").click();;
			}
			
			String ans = driver.findElementByAndroidUIAutomator("new UiSelector().resourceId(\"com.calculator:id/ans\")").getText(); 
			
			if( !checkString(ans) ){
				System.out.println("Fail"  + num1 +" " +num2 +" " +op+" -" +ans+"-");
				Label labelN1 = new Label(0, iF, num1);
				sheetF.addCell(labelN1);
				Label labelN2 = new Label(1, iF, num2);
				sheetF.addCell(labelN2);
				Label labelOp = new Label(2, iF, op);
				sheetF.addCell(labelOp);
				Label labelAns = new Label(3, iF, ans);
				sheetF.addCell(labelAns);
				iF++;
			} else {
				System.out.println("Success" + num1 +" " +num2 +" " +op+" " +ans);
				Label labelN1 = new Label(0, iS, num1);
				sheetS.addCell(labelN1);
				Label labelN2 = new Label(1, iS, num2);
				sheetS.addCell(labelN2);
				Label labelOp = new Label(2, iS, op);
				sheetS.addCell(labelOp);
				Label labelAns = new Label(3, iS, ans);
				sheetS.addCell(labelAns);
				iS++;
			}
		}
	}
	
	@After
	public void output() throws IOException, WriteException{
		writeBookF.write(); 
		writeBookF.close();
		writeBookS.write(); 
		writeBookS.close();
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
