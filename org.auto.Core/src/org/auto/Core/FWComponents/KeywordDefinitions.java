package org.auto.Core.FWComponents;

import org.openqa.selenium.WebDriver;

public class KeywordDefinitions {
	
	public static boolean performStepAction(WebDriver browserDriver, BeanClass DriverBeanSession, String stepDescription, String stepKW, String stepObject, String stepData, String stepOutputData) throws Exception{
		
		try{
			
			boolean bStepExecutionStatus=false;
			ObjectOperations objectOperations=new ObjectOperations(DriverBeanSession);
			
			switch(stepKW.toUpperCase()){
			case "CLICK":
				bStepExecutionStatus=objectOperations.clickLink(browserDriver, stepObject);
				break;
			case "ENTERTEXT":
				bStepExecutionStatus=objectOperations.setInputValue(browserDriver, stepObject, stepData);
				break;
			case "SELECTCHECKBOX":
				bStepExecutionStatus=objectOperations.selectCheckBox(browserDriver, stepObject, stepData);
				break;
			case "SELECTRADIOBUTTON":
				bStepExecutionStatus=objectOperations.selectRadioButton(browserDriver, stepObject, stepData);
				break;
			case "SELECTLISTVALUE":
				bStepExecutionStatus=objectOperations.selectListValue(browserDriver, stepObject, stepData);
				break;
			case "GETELEMENTTEXT":
				bStepExecutionStatus=objectOperations.getElementText(browserDriver, stepObject, stepOutputData);
				break;
			case "CHECKELEMENTISPRESENT":
				bStepExecutionStatus=objectOperations.checkElementExists(browserDriver, stepObject, stepData);
				break;
			case "CHECKPAGEDISPLAYED":
				bStepExecutionStatus=objectOperations.checkPageLoaded(browserDriver,stepData);
				break;
			}
			if(bStepExecutionStatus){
				return true;
			}else{
				return false;
			}	
		}catch(Exception excepObject){
			log.error("Exception in performStepAction method: "+excepObject.getMessage());
			System.out.println("Exception in performStepAction method: "+excepObject.getMessage());
			return false;
		}
	}
}
