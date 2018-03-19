package org.auto.Core.FWComponents;

import java.io.IOException;
import java.util.NoSuchElementException;


public class NoSuchElement extends NoSuchElementException{

	private static String message;
	
	public NoSuchElement(String ExceptionMessage){
		
		/*String stempTCName = DriverScript.sTCName;*/
		//Reporter.ReportStep(stempTCName, "Searching for field in application", "Field with defined property should be displayed", ExceptionMessage, "FAIL");
		message = ExceptionMessage;
		log.error(ExceptionMessage);
	}
	
	public static String errorMessage(){
		return message;
	}
		
}
