package org.auto.Core.FWComponents;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

public class log {

	private static Logger logLog = Logger.getLogger(Log.class.getName());
	
	// This is to print log for the beginning of the test case, as we usually run so many test cases as a test suite
	 
	 public static void startTestCase(String sTestCaseName){
	 
		logLog.info("****************************************************************************************");
	 
		logLog.info("****************************************************************************************");
	 
		logLog.info("$$$$$$$$$$$$$$$$$$$$$                 "+sTestCaseName+ "       $$$$$$$$$$$$$$$$$$$$$$$$$");
	 
		logLog.info("****************************************************************************************");
	 
		logLog.info("****************************************************************************************");
	 
		}
	 
		//This is to print log for the ending of the test case
	 
	 public static void endTestCase(String sTestCaseName){
	 
		logLog.info("XXXXXXXXXXXXXXXXXXXXXXX             "+"-E---N---D-"+"             XXXXXXXXXXXXXXXXXXXXXX");
	 
		logLog.info("X");
	 
		logLog.info("X");
	 
		logLog.info("X");
	 
		logLog.info("X");
	 
		}
	 
	 public static void info(String message){
		 logLog.info(message);		
	 }
	 
	 public static void warn(String message){
		 logLog.warn(message);
	 }
	 
	 public static void error(String message){
		 logLog.error(message);
	 }
	 
	 public static void fatal(String message){
		 logLog.fatal(message);
	 }
	 
	 public static void debug(String message){
		 logLog.debug(message);
	 }
	 
}
