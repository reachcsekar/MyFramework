package org.auto.Core.FWComponents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class ObjectOperations {
	
		
	private static String sCurrentModuleName, sExpectedResult, sActualResult;
	private static BeanClass objRunInstances;
	//Hash map to store user defined variables and their values
	private Hashtable<String,String> userVariables=new Hashtable<String,String>();
			
	public ObjectOperations(BeanClass DriveBeanInstance){
		objRunInstances=DriveBeanInstance;
	}
	
	@SuppressWarnings("finally")
	private WebElement returnElement(WebDriver driver,String ObjectName) throws NoSuchElementException{
		
		WebElement element = null;
		String sObjectPropertyValue = "", sPropertyName = "",sPropertyValue = "";
		Hashtable<String,String> objTempObjectHash=new Hashtable<String,String>();
		
		try{
			sCurrentModuleName=objRunInstances.getsModuleName();
			if (ExcelUtils.objObjectDetails.containsKey(sCurrentModuleName)){
				objTempObjectHash = ExcelUtils.objObjectDetails.get(sCurrentModuleName);
				sObjectPropertyValue=objTempObjectHash.get(ObjectName);
				sPropertyName = sObjectPropertyValue.split("=",2)[0];
				sPropertyValue = sObjectPropertyValue.split("=",2)[1];
				switch(sPropertyName.toUpperCase()){
					case "ID":
						element = driver.findElement(By.id(sPropertyValue));
						break;
					case "NAME":
						element = driver.findElement(By.name(sPropertyValue));
						break;
					case "XPATH":
						element = driver.findElement(By.xpath(sPropertyValue));
						break;
					case "LINKTEXT":
						element = driver.findElement(By.linkText(sPropertyValue));
						break;
					case "PARTIALLINKTEXT":
						element = driver.findElement(By.partialLinkText(sPropertyValue));
						break;
					default:
						//log.error("The locator -- "+sPropertyName+" is not defined.");
						break;
				}			
				return element;
			}
		
		}catch (NoSuchElementException e){		
			System.out.println(e.getMessage());
			//throw new NoSuchElement(e+"The object -- "+ObjectName+" with locator value -- "+sPropertyName+" is not displayed in application.");
			log.error("The object -- "+ObjectName+" with locator value -- "+sPropertyName+" is not displayed in application."+e.getMessage());
		}
		finally{
			return element;
		}
	}
	
	@SuppressWarnings("finally")
	private List<WebElement> returnElements(WebDriver driver,String ObjectName) throws NoSuchElementException{
		
		List<WebElement> elementList=new ArrayList<WebElement>();
		String sObjectPropertyValue = "", sPropertyName = "",sPropertyValue = "";
		Hashtable<String,String> objTempObjectHash=new Hashtable<String,String>();
		
		try{
			sCurrentModuleName=objRunInstances.getsModuleName();
			if (ExcelUtils.objObjectDetails.containsKey(sCurrentModuleName)){
				objTempObjectHash = ExcelUtils.objObjectDetails.get(sCurrentModuleName);
				sObjectPropertyValue=objTempObjectHash.get(ObjectName);
				sPropertyName = sObjectPropertyValue.split("=",2)[0];
				sPropertyValue = sObjectPropertyValue.split("=",2)[1];
				switch(sPropertyName.toUpperCase()){
					case "ID":
						elementList = driver.findElements(By.id(sPropertyValue));
						break;
					case "NAME":
						elementList = driver.findElements(By.name(sPropertyValue));
						break;
					case "XPATH":
						elementList = driver.findElements(By.xpath(sPropertyValue));
						break;
					case "LINKTEXT":
						elementList = driver.findElements(By.linkText(sPropertyValue));
						break;
					case "PARTIALLINKTEXT":
						elementList = driver.findElements(By.partialLinkText(sPropertyValue));
						break;
					default:
						//log.error("The locator -- "+sPropertyName+" is not defined.");
						break;
				}			
				return elementList;
			}
		
		}catch (NoSuchElementException e){		
			System.out.println(e.getMessage());
			//throw new NoSuchElement(e+"The object -- "+ObjectName+" with locator value -- "+sPropertyName+" is not displayed in application.");
			log.error("The object -- "+ObjectName+" with locator value -- "+sPropertyName+" is not displayed in application."+e.getMessage());
		}
		finally{
			return elementList;
		}
		
		
	}

	protected boolean setInputValue(WebDriver brwDriver,String ObjectName, String InputValue) throws AssertionError, IOException{
		
		WebElement InputObject;
		InputObject = returnElement(brwDriver,ObjectName);
		sExpectedResult="The value -- "+InputValue+" should be entered in text box -- "+ObjectName;		
		if (InputObject != null){
			//if (InputObject.isDisplayed()){
				InputObject.sendKeys(InputValue);
				sActualResult="The value -- "+InputValue+" has been entered in text box -- "+ObjectName;
				//log.info(sActualResult);				
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
				return true;
			/*}else{				
				sActualResult="The object -- "+ObjectName+"is not displayed";
				//log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				return false;
			}*/
		}else{
			sActualResult="The description for the field -- "+ObjectName+" is not valid. Please enter valid value for locators.";
			//log.error(sActualResult);
			Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
			return false;
		}
	}
	
	public boolean clickLink(WebDriver brwDriver,String ObjectName) throws Exception{
		
		WebElement ClickObject;
		ClickObject = returnElement(brwDriver,ObjectName);
		sExpectedResult="The link -- "+ObjectName+" should be clicked";
		if (ClickObject != null){
			if (ClickObject.isDisplayed()){
				ClickObject.click();								
				sActualResult="The link -- "+ObjectName+" has been clicked";
				//log.info(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
				return true;
			}else{
				sActualResult="The object -- "+ObjectName+"is not displayed";
				//log.error(sActualResult);				
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				return false;
			}
		}else{
			sActualResult="The description for the field -- "+ObjectName+" is not valid. Please enter valid value for locators.";
			//log.error(sActualResult);
			Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
			return false;
		}
		
		
	}
	
	public boolean selectListValue(WebDriver brwDriver,String ObjectName, String ListValue) throws Exception{
		int iValueFoundFlag = 0;
		WebElement ListObject;
		int listValueIndex=0;
		
		try{
			
			ListObject = returnElement(brwDriver,ObjectName);
			sExpectedResult="The value -- "+ListValue+" should be selected from list box -- "+ObjectName;
			if (ListObject != null){
				//if (ListObject.isDisplayed()){
					Select oSelectionObject = new Select(ListObject);
					List<WebElement> lListValues = oSelectionObject.getOptions();
					for (WebElement lValues:lListValues){						
						listValueIndex=listValueIndex+1;
						if (lValues.getText().equals(ListValue)){																				
							//oSelectionObject.selectByIndex(listValueIndex);
							oSelectionObject.selectByVisibleText(ListValue);							
							iValueFoundFlag = 1;
							sActualResult="The value -- "+ListValue+" has been selected from list box -- "+ObjectName;
							//log.info(sActualResult);
							Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
							break;
						}					
					}
					if (iValueFoundFlag == 0){
						sActualResult="The value -- "+ListValue+" is unavailable in list box -- "+ObjectName;
						//log.error(sActualResult);
						Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
						return false;
					}
				/*}else{
					sActualResult="The object -- "+ObjectName+"is not displayed";
				//	log.error(sActualResult);
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				}*/
			}else{
				sActualResult="The description for the field -- "+ObjectName+" is not valid. Please enter valid value for locators.";
				//log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
			}
			return true;
		}catch(Exception excepObject){
			System.out.println("Exception in selectListValue method: "+excepObject.getMessage());
			log.error("Exception in selectListValue method: "+excepObject.getMessage());
			return false;
		}	
		
	}
	
	public boolean selectRadioButton(WebDriver driver,String ObjectName,String OptionValue) throws Exception{
		
		List<WebElement> lRadioGroups = new ArrayList<WebElement>();		
		String sObjectPropertyValue, sPropertyName,sPropertyValue,sOptionValue;
		int iOptionfoundFlag = 0;
		Hashtable<String,String> objTempObjectHash=new Hashtable<String,String>();
		
		sExpectedResult="The value -- "+OptionValue+" should be selected from radio group -- "+ObjectName;
		try{			
			sCurrentModuleName=objRunInstances.getsModuleName();
			if (ExcelUtils.objObjectDetails.containsKey(sCurrentModuleName)){
				objTempObjectHash = ExcelUtils.objObjectDetails.get(sCurrentModuleName);
				sObjectPropertyValue=objTempObjectHash.get(ObjectName);
				sPropertyName = sObjectPropertyValue.split("=")[0];
				sPropertyValue = sObjectPropertyValue.split("=")[1];
				switch(sPropertyName){
					case "ID":
						lRadioGroups = driver.findElements(By.id(sPropertyValue));
						break;
					case "Name":
						lRadioGroups = driver.findElements(By.name(sPropertyValue));
						break;
					case "XPath":
						lRadioGroups = driver.findElements(By.xpath(sPropertyValue));
						break;				
					default:
						throw new NoSuchElement("The object -- "+ObjectName+" is not defined correctly.");
				}	
				objTempObjectHash.clear();
		}
		}catch(Exception e){
			//System.out.println(e.getMessage());	
			sActualResult="The radio group object -- "+ObjectName+"  is not displayed in application."+e.getMessage();
		//	log.error(sActualResult);
			Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
			return false;
		}
			
		if(lRadioGroups.size() != 0){
			for(WebElement tempElement:lRadioGroups){
				sOptionValue = tempElement.getAttribute("value");
				if (sOptionValue.equals(OptionValue)){
					tempElement.click();
					iOptionfoundFlag = 1;
					sActualResult="The value -- "+OptionValue+" has been selected from radio group -- "+ObjectName;
					//log.info(sActualResult);
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
					break;
				}
			}
			if (iOptionfoundFlag == 0){
				sActualResult="The option -- "+OptionValue+"is not available in radio group - "+ObjectName;
				//log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				return false;
			}
			return true;
		}else{
			sActualResult="The description for the field -- "+ObjectName+" is not valid. Please enter valid value for locators.";
			//log.error(sActualResult);
			Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
			return false;
		}
		
	}
	
	public boolean selectCheckBox(WebDriver driver,String ObjectName,String OptionValue) throws Exception{
		
		List<WebElement> lCheckBox = new ArrayList<WebElement>();		
		String sObjectPropertyValue, sPropertyName,sPropertyValue,sOptionValue;
		int iOptionfoundFlag = 0;
		Hashtable<String,String> objTempObjectHash=new Hashtable<String,String>();
		
		sExpectedResult="The value -- "+OptionValue+" should be selected from check box group -- "+ObjectName;
		try{			
			sCurrentModuleName=objRunInstances.getsModuleName();
			if (ExcelUtils.objObjectDetails.containsKey(sCurrentModuleName)){
				objTempObjectHash = ExcelUtils.objObjectDetails.get(sCurrentModuleName);
				sObjectPropertyValue=objTempObjectHash.get(ObjectName);
				sPropertyName = sObjectPropertyValue.split("=")[0];
				sPropertyValue = sObjectPropertyValue.split("=")[1];
				switch(sPropertyName){
					case "ID":
						lCheckBox = driver.findElements(By.id(sPropertyValue));
						break;
					case "Name":
						lCheckBox = driver.findElements(By.name(sPropertyValue));
						break;
					case "XPath":
						lCheckBox = driver.findElements(By.xpath(sPropertyValue));
						break;				
					default:
						throw new NoSuchElement("The object -- "+ObjectName+" is not defined correctly.");
				}
				objTempObjectHash.clear();				
			}	
		}catch(Exception e){
			//System.out.println(e.getMessage());
			sActualResult="The check box object -- "+ObjectName+"  is not displayed in application."+e.getMessage();
			//log.error(sActualResult);
			Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
			return false;
		}
		
		if(lCheckBox.size() != 0){
			for(WebElement tempElement:lCheckBox){
				sOptionValue = tempElement.getAttribute("value");
				if (sOptionValue.equals(OptionValue)){
					tempElement.click();
					iOptionfoundFlag = 1;
					sActualResult="The value -- "+OptionValue+" has been selected from check box group -- "+ObjectName;
					//log.info(sActualResult);
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
					break;					
				}
			}
			if (iOptionfoundFlag == 0){
				sActualResult="The option -- "+OptionValue+"is not available in check box group - "+ObjectName;
				//log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				return false;
			}
			return true;
		}else{
			sActualResult="The description for the field -- "+ObjectName+" is not valid. Please enter valid value for locators.";
			//log.error(sActualResult);
			Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
			return false;
		}
		
	}
	
	protected boolean getElementText(WebDriver brwDriver,String ObjectName,String outputVariable){
		
		//Variable Declaration
		WebElement pageElement;
		String pageElementText;
		try{
			
			//Retrieving page field description
			pageElement=returnElement(brwDriver,ObjectName);
			sExpectedResult="The text displayed in element -- "+ObjectName+" should be captured";
			//Checking whether the field description is valid
			if (pageElement != null){
				//Checking whether field is displayed
				if (pageElement.isDisplayed()){
					//Retrieving the text displayed in field
					pageElementText=pageElement.getText();	
					//Checking whether the output data is to be stored in a variable
					if(outputVariable!="" && outputVariable!=null){
						//Storing captured field text in user defined variable
						userVariables.put(outputVariable, pageElementText);
					}					
					//Reporting the captured text and step result
					sActualResult="The text -- "+pageElementText+" is displayed in/for element -- "+ObjectName+" and has been stored in output variable";
					//log.info(sActualResult);
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
					return true;
				}else{
					sActualResult="The object -- "+ObjectName+"is not displayed";						
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
					return false;
				}
			}else{
				sActualResult="The description for the field -- "+ObjectName+" is not valid. Please enter valid value for locators.";
				log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				return false;
			}
		}catch(Exception excepObject){
			log.error("Exception in getElementText method: "+excepObject.getMessage());
			return false;
		}
		
	}
	
	protected boolean checkElementExists(WebDriver brwDriver,String ObjectName,String assertVerify){
		
		//Variable Declaration
		WebElement pageElement;
		
		try{
			//Retrieving page field description
			pageElement=returnElement(brwDriver,ObjectName);
			sExpectedResult="The field -- "+ObjectName+" should exist in application";
			//Checking whether the field description is valid
			if (pageElement != null){
				//Checking whether field is displayed
				if (pageElement.isDisplayed()){							
					//Reporting the presence of element in application
					sActualResult="The field -- "+ObjectName+" is available in application and is displayed";
					//log.info(sActualResult);
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
					return true;
				}else{
					sActualResult="The field -- "+ObjectName+"is not displayed";						
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
					//checking whether the step is for asserting or for verifying. Returning false if assert
					if(assertVerify.equalsIgnoreCase("assert")){					
						return false;
					}else{
						return true;
					}
					
				}
			}else{
				sActualResult="The description for the field -- "+ObjectName+" is not valid. Please enter valid value for locators.";
				log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				//checking whether the step is for asserting or for verifying. Returning false if assert
				if(assertVerify.equalsIgnoreCase("assert")){					
					return false;
				}else{
					return true;
				}
			}
		}catch(Exception excepObject){
			log.error("Exception in checkElementExists method: "+excepObject.getMessage());
			return false;
		}
		
	}
		
	protected boolean checkElementNotExists(WebDriver brwDriver,String ObjectName,String assertVerify){
		
		//Variable Declaration
		List<WebElement> elementList=new ArrayList<WebElement>();
		
		try{
			//Retrieving page field description
			elementList=returnElements(brwDriver,ObjectName);
			sExpectedResult="The field -- "+ObjectName+" should not exist in application";
			//Checking whether the list of web elements count is zero
			if (elementList.size()==0){			
				//Reporting that the element is not present in application
				sActualResult="The field -- "+ObjectName+" not is available in application";
				//log.info(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
				return true;
			}else{
				sActualResult="The field -- "+ObjectName+" is available in application";
				log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				//checking whether the step is for asserting or for verifying. Returning false if assert
				if(assertVerify.equalsIgnoreCase("assert")){					
					return false;
				}else{
					return true;
				}
				
			}
		}catch(Exception excepObject){
			log.error("Exception in checkElementNotExists method: "+excepObject.getMessage());
			return false;
		}	
	}
	
	protected boolean checkPageLoaded(WebDriver brwDriver,String pageTitle){
				
		//Variable declaration
		String currentPageTitle;
		
		try{			
			//Retrieving current page title
			currentPageTitle=brwDriver.getTitle();
			//Checking whether expected page title is present in actual page title
			if (currentPageTitle.contains(pageTitle)){				
				sExpectedResult="The page with title - "+pageTitle+" should be loaded";
				//Reporting that the expected page title is loaded
				sActualResult="Page with the title -- "+pageTitle+" is loaded.";
				//log.info(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
				return true;				
			}else{
				sActualResult="Page with the title -- "+pageTitle+" is not loaded.";
				log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				return false;
			}
		}catch(Exception excepObject){
			log.error("Exception in checkPageLoaded method: "+excepObject.getMessage());
			return false;
		}
	}
	
	protected boolean mouseHoverOnMenuItem(WebDriver brwDriver,String ObjectName){
		
		//Variable Declaration
		WebElement menuElement;
		try{
			//Retrieving page field description
			menuElement=returnElement(brwDriver,ObjectName);
			sExpectedResult="Mouse hover on menu item -- "+ObjectName+" in application";
			//Checking whether the field description is valid
			if (menuElement != null){
				//Checking whether field is displayed
				if (menuElement.isDisplayed()){				
					//Creating object for Actions class
					Actions actionObject=new Actions(brwDriver);
					//Mouse hovering on menu item
					actionObject.moveToElement(menuElement).perform();
					//Reporting that mouse hover is performed on menu item
					sActualResult="Mouse hover on menu item -- "+ObjectName+" in application is successful";
					log.info(sActualResult);
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "PASS");
					return true;
				}else{
					sActualResult="Mouse hover on menu item -- "+ObjectName+" in application is unsuccessful. Menu item not displayed";						
					Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");					
					return false;					
				}
			}else{
				sActualResult="The description for the menu item -- "+ObjectName+" is not valid. Please enter valid value for locators.";
				log.error(sActualResult);
				Reporter.ReportStep(objRunInstances.getsCurrentTCName(), objRunInstances.getsCurrentStepDesc(), sExpectedResult, sActualResult, "FAIL");
				return false;
			}						
		}
		catch(Exception excepObject){
			log.error("Exception in mouseHoverOnMenuItem method: "+excepObject.getMessage());
			return false;
		}
	}
}
