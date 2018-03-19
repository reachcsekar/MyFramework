package org.auto.Driver;



import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

//import org.apache.log4j.xml.DOMConfigurator;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.auto.Core.FWComponents.BeanClass;
import org.auto.Core.FWComponents.ExcelUtils;
import org.auto.Core.FWComponents.KeywordDefinitions;
import org.auto.Core.FWComponents.Reporter;
//import org.auto.Core.FWComponents.log;


//import org.apache.log4j.xml.DOMConfigurator;


public class DriverScript {

	
	//public static WebDriver browserDriver;
	DriverScript driverSession=new DriverScript(); 
	
	
	public static void main(String[] args) throws Exception{
				
		String sTCName,sExecuteFlag, sCurrentStepDetails, sStepInputData;
		int iIterVar, iCurrentIterRow, iIterCountVal,iCurrentTestScriptStepIndex;
		boolean bStepExecStatus;		
		Hashtable<Integer,String> objCurrentTCSteps=new Hashtable<Integer,String>();		
		String[] arrStepDetails;
		//BeanClass beanSession;		
		int iTCIndex,iTCCount;	
		BeanClass objBeanSession=new BeanClass();
		objBeanSession.loadEnvironmentVariables();
		System.setProperty("webdriver.gecko.driver",objBeanSession.getEnvironmentVariable("Gecko"));
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
		//Configuration of log4j messages
		//DOMConfigurator.configure("log4j.xml");
		//log.startTestCase("Start of Driver Script");	
		//Create reporting file
		Reporter.setReportFile();
		//Creating an instance for Excel class
		ExcelUtils objExcelUtilObject=new ExcelUtils(System.getProperty("user.dir"));		
		//Retrieve column index of columns available in different modules of input data sheet and load them in a hash table
		objExcelUtilObject.getColumnIndexInputData();				
		//Retrieve and load object details in a hash table.
		objExcelUtilObject.getObjectDetails();
		//Retrieve and load test script steps in a hash table
		objExcelUtilObject.getTestScripts();
		//Initiating object for BeanClass instances
		
		
		//Initiating Web Driver instance
		WebDriver webDriver=new ChromeDriver();		
		//webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
		//Retrieve test case count and save in variable
		iTCCount = (objExcelUtilObject.getSheetRowCount("Driver")-1);
		System.out.println("Total test case: "+iTCCount);
		//Looping through rows/test cases listed in 'Driver' sheet
		for (iTCIndex=1;iTCIndex<=iTCCount;iTCIndex++){			
			//Retrieve and store test case/method name
			sTCName = objExcelUtilObject.getCellData("Driver", iTCIndex, "TCName");
			System.out.println("Current test case name: "+sTCName);
			//Setting the current test case name in BeanClass instance
			objBeanSession.setsCurrentTCName(sTCName);
			//Retrieve current module name and save it in BeanClass instance
			objBeanSession.setsModuleName(objExcelUtilObject.getCellData("Driver", iTCIndex, "ModuleName"));			
			//Retrieve execution control for the test case
			sExecuteFlag = objExcelUtilObject.getCellData("Driver", iTCIndex, "ExecuteFlag");
			//Check whether the test case should be execute.
			if (sExecuteFlag.equals("Y")){				
				//Retrieving iteration details
				objExcelUtilObject.getIterationDetails(objBeanSession.getsModuleName(), sTCName);
				objBeanSession.setiIterStartRow(objExcelUtilObject.getIterationStartRow());
				objBeanSession.setiIterEndRow(objExcelUtilObject.getIterationEndRow());
				objBeanSession.setiTotalIterCount(objExcelUtilObject.getIterationCount());
				//Retrieve iteration start row
				iCurrentIterRow=objBeanSession.getiIterStartRow();
				//Retrieve total iteration count
				iIterCountVal=objBeanSession.getiTotalIterCount();
				//Retrieve current test case steps
				objCurrentTCSteps=objExcelUtilObject.getCurrentTestScriptSteps(sTCName, objBeanSession.getsModuleName());
				//Launching application
				webDriver.get(objBeanSession.getEnvironmentVariable("TestURL"));
				webDriver.manage().window().maximize();
				//Thread.sleep(3000);
				//Looping through iterations
				for(iIterVar=1;iIterVar<=iIterCountVal;iIterVar++){		
					//Looping through test script steps
					for(iCurrentTestScriptStepIndex=1;iCurrentTestScriptStepIndex<=objCurrentTCSteps.size();iCurrentTestScriptStepIndex++){
						//Initiating step execution status
						bStepExecStatus=false;
						//Retrieve current step details
						sCurrentStepDetails=objCurrentTCSteps.get(iCurrentTestScriptStepIndex);
						//Replace delimiter string at the beginning of string
						sCurrentStepDetails=sCurrentStepDetails.replaceFirst(";", "");
						//Split step details to retrieve different details
						arrStepDetails=sCurrentStepDetails.split(";");
						//Saving current step description in bean session
						objBeanSession.setsCurrentStepDesc(arrStepDetails[0]);
						//Checking whether the input data has reference to a column present in input data sheet 
						if(arrStepDetails[3].contains("@")){
							sStepInputData=objExcelUtilObject.getCellData(objBeanSession.getsModuleName(), iCurrentIterRow, arrStepDetails[3].replaceAll("@", ""));
						}else{
							sStepInputData=arrStepDetails[3];
						}
						//Executing current test script step
						bStepExecStatus=KeywordDefinitions.performStepAction(webDriver, objBeanSession, arrStepDetails[0], arrStepDetails[1], arrStepDetails[2], sStepInputData, arrStepDetails[4]);
						//Breaking out of current iteration if the step has failed
						if(bStepExecStatus==false){
							break;
						}
					}
					//Increasing iteration row index for next iteration
					iCurrentIterRow=iCurrentIterRow+1;
				}
				//webDriver.close();
			}
		}
		
		
		
		//log.endTestCase("End of Driver Script");
		
		//Generate final html report
		Reporter.generateFinalReport();
	}
	
}
