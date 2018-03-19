package org.auto.Core.FWComponents;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ExcelUtils {
	
	//Creating objects for excel sheet, workbook, cell, and row
	private org.apache.poi.ss.usermodel.Sheet ExcelSheet;
	private org.apache.poi.ss.usermodel.Workbook ExcelWorkbook;
	private org.apache.poi.ss.usermodel.Cell ExcelCell;
	private org.apache.poi.ss.usermodel.Row ExcelRow;
	//Static variable to hold total number of scenarios
	public static int iTotalScenarios;
	//Static variables to hold values of input data sheet, 
	private static String sInputDataSheetPath, sORPath, sTestScriptFilePath;
	//Objects to refer to module sheet and script sheet
	org.apache.poi.ss.usermodel.Sheet objModuleSheet, objScriptSheet;
	//Hash table to hold column index of all used columns in all sheets of input data sheet
	private static Hashtable<String,Hashtable> objColumnDictionary = new Hashtable<String,Hashtable>();
	//Hash table to hold column index of all used column in all sheets of object repository
	public static Hashtable<String,Hashtable> objObjectDetails = new Hashtable<String,Hashtable>();  //Declared as public as the variable is used in OpjectOperations class
	//Hash table to 
	private static Hashtable<String,Hashtable> objTestScriptSteps=new Hashtable<String,Hashtable>();
	private static BeanClass objExcelBeanObject;
	private static MissingCellPolicy blankCellValue;
	
	public ExcelUtils(String ProjectFolderPath) throws Exception{
	
		sInputDataSheetPath=ProjectFolderPath+"\\TestData\\TestData.xls";
		sORPath=ProjectFolderPath+"\\ObjectRepository\\OR.xls";
		sTestScriptFilePath=ProjectFolderPath+"\\TestScripts\\TestScripts.xls";
		setExcelFile(sInputDataSheetPath, "InputData");
		objExcelBeanObject=new BeanClass();
	}
	
	public void setExcelFile(String Path, String SheetName) throws Exception{
		try{
			//Open excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			//Access the required test data sheet
			ExcelWorkbook=WorkbookFactory.create(ExcelFile);					
			}catch (Exception e){
				throw e;
			}
	}

	public int getSheetRowCount(String SheetName){
		return ExcelWorkbook.getSheet(SheetName).getPhysicalNumberOfRows();
	}
	
	
	public String getCellData(String SheetName,int rowNum, String colName) throws Exception{
		org.apache.poi.ss.usermodel.Sheet TempSheet;
		int colNum = -1;
		try{
			Hashtable<String,Integer> tempHashTab=objColumnDictionary.get(SheetName);
			colNum=tempHashTab.get(colName);
			if(colNum!=-1){
				TempSheet = ExcelWorkbook.getSheet(SheetName);
				ExcelCell = TempSheet.getRow(rowNum).getCell(colNum);
				String cellData = ExcelCell.getStringCellValue();
				return cellData;
			}else{
				return null;
			}
						
		}catch (Exception e){
			return "";
		}
	}
	
	public void getIterationDetails(String CurrentModuleName, String CurrentTCName) throws Exception{
		
		//Flag variable to ascertain whether start row is found
		int iStartRowFound=0,iModuleRows;
		String sTempCellValue;
		//Set iteration start and end row as 0
		objExcelBeanObject.setiIterStartRow(0);
		objExcelBeanObject.setiIterEndRow(0);
		//Retrieve module sheet
		objModuleSheet=ExcelWorkbook.getSheet(CurrentModuleName);
		//Get row count of module sheet
		iModuleRows=objModuleSheet.getPhysicalNumberOfRows();
		//Start iterating through rows in module sheet
		for(int iModuleRowsIter=1;iModuleRowsIter<iModuleRows;iModuleRowsIter++){
			//Retrieving test case name
			sTempCellValue=getCellData(CurrentModuleName,iModuleRowsIter,"TCName");
			//Checking whether retrieved test case name matches the current test case name
			if(sTempCellValue.equalsIgnoreCase(CurrentTCName) && iStartRowFound==0){
				//Setting the start row for iteration
				objExcelBeanObject.setiIterStartRow(iModuleRowsIter);
				iStartRowFound=1;
			}else if(!(sTempCellValue.equalsIgnoreCase(CurrentTCName)) && iStartRowFound!=0){
				//Setting end row for iteration
				objExcelBeanObject.setiIterEndRow(iModuleRowsIter-1);	
				break;
			}
			if(iModuleRowsIter==(iModuleRows-1)){
				objExcelBeanObject.setiIterEndRow(iModuleRowsIter);
			}
		}
		int iTotalIterations=(objExcelBeanObject.getiIterEndRow()-objExcelBeanObject.getiIterStartRow())+1;
		objExcelBeanObject.setiTotalIterCount(iTotalIterations);		
	}
	
	public int getIterationStartRow(){
		return objExcelBeanObject.getiIterStartRow();
	}
	
	public int getIterationEndRow(){
		return objExcelBeanObject.getiIterEndRow();
	}
	
	public int getIterationCount(){
		return objExcelBeanObject.getiTotalIterCount();
	}
		
	public void setCellData(String Result, int rowNum, int colNum) throws Exception{
		try{
			ExcelRow = ExcelSheet.getRow(rowNum);
			ExcelCell = ExcelRow.getCell(colNum,Row.RETURN_BLANK_AS_NULL);
			if (ExcelCell == null){
				ExcelCell = ExcelRow.createCell(colNum);
				ExcelCell.setCellValue(Result);						
			}else{
				ExcelCell.setCellValue(Result);
			}
			//sInputDataSheetPath=System.getProperty("user.dir").replace('\\', '/')+"/TestData.xlsx";
			FileOutputStream fileOut = new FileOutputStream(sInputDataSheetPath);
			ExcelWorkbook.write(fileOut);
			fileOut.flush();
			fileOut.close();			
		}catch (Exception e){
			throw e;
		}
	}
	
	public void getColumnIndexInputData() throws Exception{
		
		int colIndex,icolcount;				
		String cellData;
		org.apache.poi.ss.usermodel.Workbook objInputDataFile;
		org.apache.poi.ss.usermodel.Sheet objectSheet;
		Iterator<org.apache.poi.ss.usermodel.Sheet> objSheetIter;
		Hashtable<String,Integer> objTempColumnDictionary = new Hashtable<String,Integer>();
		
		try{																		
			//objInputDataFile=new XSSFWorkbook(new FileInputStream(sInputDataSheetPath));
			objInputDataFile=WorkbookFactory.create(new FileInputStream(sInputDataSheetPath));
			objSheetIter=objInputDataFile.iterator();
			while(objSheetIter.hasNext()){
				objectSheet=objSheetIter.next();
				ExcelRow = objectSheet.getRow(0);						
				icolcount = ExcelRow.getLastCellNum();
				for(colIndex = 0;colIndex<icolcount;colIndex++){				
					ExcelCell = ExcelRow.getCell(colIndex);
					cellData = ExcelCell.getStringCellValue();
					objTempColumnDictionary.put(cellData, colIndex);											
				}
				objColumnDictionary.putIfAbsent(objectSheet.getSheetName(), (Hashtable) objTempColumnDictionary.clone());
				//objColumnDictionary.
				objTempColumnDictionary.clear();
			}
			objInputDataFile.close();
		}catch (Exception e){
			throw e;			
			
		}	
		
	}
	
	public void getTestScripts() throws Exception{
				
		int iStepCounter=1,iTempColIndex;
		String sStepDetails="",sPrevTCName="",sTempTCName,sNextTCName, sCurrTCName="",sCurrentCellDetails="";
		org.apache.poi.ss.usermodel.Workbook objScriptFile;
		org.apache.poi.ss.usermodel.Cell objCurrentCell;
		Iterator<org.apache.poi.ss.usermodel.Sheet> objSheetIter;
		Hashtable<Integer,String> objTempScriptSteps=new Hashtable<Integer,String>();
		Hashtable<String,Hashtable> objTempScriptStepsConsolidated=new Hashtable<String,Hashtable>();
		
		//objScriptFile = new XSSFWorkbook(new FileInputStream(sTestScriptFilePath));		
		objScriptFile=WorkbookFactory.create(new FileInputStream(sTestScriptFilePath));
		objSheetIter=objScriptFile.iterator();
		while(objSheetIter.hasNext()){
			objScriptSheet=objSheetIter.next();
			int iTempRowCount=objScriptSheet.getPhysicalNumberOfRows();
			for(int iTempRowIndex=1;iTempRowIndex<iTempRowCount;iTempRowIndex++){
				sStepDetails="";				
				objCurrentCell=objScriptSheet.getRow(iTempRowIndex).getCell(0,blankCellValue.RETURN_BLANK_AS_NULL);
				if(objCurrentCell==null){
					sTempTCName="";
				}else{
					sTempTCName=objScriptSheet.getRow(iTempRowIndex).getCell(0).getStringCellValue();
				}
				
				if(objTempScriptSteps.isEmpty()){
					sCurrTCName=sTempTCName;
				}
				if(sTempTCName==sCurrTCName || sTempTCName==""){
					for(iTempColIndex=1;iTempColIndex<=5;iTempColIndex++){
						objCurrentCell=objScriptSheet.getRow(iTempRowIndex).getCell(iTempColIndex, blankCellValue.RETURN_BLANK_AS_NULL);
						if(objCurrentCell==null){
							sCurrentCellDetails="empty";
						}else{
							sCurrentCellDetails=objCurrentCell.getStringCellValue();
							
						}
						sStepDetails=sStepDetails+";"+sCurrentCellDetails;
					}
					objTempScriptSteps.putIfAbsent(iStepCounter, sStepDetails);
					iStepCounter=iStepCounter+1;
				}else{
					objTempScriptStepsConsolidated.putIfAbsent(sCurrTCName, (Hashtable<String,String>) objTempScriptSteps.clone());
					objTempScriptSteps.clear();
					iStepCounter=1;
					sCurrTCName=sTempTCName;
					if(sTempTCName==sCurrTCName || sTempTCName==""){
						for(iTempColIndex=1;iTempColIndex<=5;iTempColIndex++){
							sCurrentCellDetails="";
							objCurrentCell=objScriptSheet.getRow(iTempRowIndex).getCell(iTempColIndex, blankCellValue.RETURN_BLANK_AS_NULL);
							if(objCurrentCell==null){
								sCurrentCellDetails="empty";
							}else{
								sCurrentCellDetails=objCurrentCell.getStringCellValue();								
							}
							sStepDetails=sStepDetails+";"+sCurrentCellDetails;
						}
						objTempScriptSteps.putIfAbsent(iStepCounter, sStepDetails);
						iStepCounter=iStepCounter+1;
					}
				}
				if(iTempRowIndex==iTempRowCount-1){
					objTempScriptStepsConsolidated.putIfAbsent(sCurrTCName, (Hashtable<String,String>) objTempScriptSteps.clone());
					objTempScriptSteps.clear();
					iStepCounter=1;
				}				
			}
			objTestScriptSteps.putIfAbsent(objScriptSheet.getSheetName(), (Hashtable<String,Hashtable>) objTempScriptStepsConsolidated.clone());
			objTempScriptStepsConsolidated.clear();
		}		
		objScriptFile.close();		
	}
	
	public Hashtable<Integer,String> getCurrentTestScriptSteps(String CurrentTCName, String CurrentModuleName){
				
		Hashtable<Integer,String> objTempScriptSteps=new Hashtable<Integer,String>();
		Hashtable<String,Hashtable> objTempScriptStepsConsolidated=new Hashtable<String,Hashtable>();
		
		objTempScriptStepsConsolidated=(Hashtable<String, Hashtable>) objTestScriptSteps.get(CurrentModuleName);
		objTempScriptSteps=(Hashtable<Integer, String>) objTempScriptStepsConsolidated.get(CurrentTCName);
		objTempScriptStepsConsolidated.clear();
		return objTempScriptSteps;
		
	}
	
		
	public void getObjectDetails() throws Exception{
		int iObjectCount,colIndex,irowIndex,icolcount;				
		String cellData,sPropertyName,sPropertyValue,sObjectName;
		org.apache.poi.ss.usermodel.Workbook objORFile;
		org.apache.poi.ss.usermodel.Sheet objectSheet;
		Iterator<org.apache.poi.ss.usermodel.Sheet> objSheetIter;
		Hashtable<String,String> objTempObjectDictionary = new Hashtable<String,String>();
		
		try{				
			//objORFile=new XSSFWorkbook(new FileInputStream(sORPath));
			objORFile=WorkbookFactory.create(new FileInputStream(sORPath));
			objSheetIter=objORFile.iterator();
			while(objSheetIter.hasNext()){
				objectSheet=objSheetIter.next();
				ExcelRow = objectSheet.getRow(0);			
				iObjectCount = objectSheet.getPhysicalNumberOfRows();
				icolcount = ExcelRow.getLastCellNum();
				for (irowIndex= 1;irowIndex<iObjectCount;irowIndex++){		
					ExcelRow = objectSheet.getRow(irowIndex);
					for(colIndex = 1;colIndex<icolcount;colIndex++){				
						ExcelCell = ExcelRow.getCell(colIndex,Row.RETURN_BLANK_AS_NULL);					
						if (ExcelCell != null){
							cellData = ExcelCell.getStringCellValue();
							sObjectName =  objectSheet.getRow(irowIndex).getCell(0).getStringCellValue();
							sPropertyName =  objectSheet.getRow(0).getCell(colIndex).getStringCellValue();
							sPropertyValue = cellData;
							objTempObjectDictionary.putIfAbsent(sObjectName, sPropertyName+"="+sPropertyValue);
							break;
						}													
					}
					if (ExcelCell == null){
						break;
					}
				}
				objObjectDetails.putIfAbsent(objectSheet.getSheetName(), (Hashtable<String,String>) objTempObjectDictionary.clone());
				objTempObjectDictionary.clear();
			}			
			objORFile.close();
		}catch (Exception e){
			throw e;						
		}	
	}		
}
