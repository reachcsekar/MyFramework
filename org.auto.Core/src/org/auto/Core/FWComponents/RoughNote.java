package org.auto.Core.FWComponents;

import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RoughNote {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		RoughNote roughNote=new RoughNote();
		//roughNote.xmlParser();
		roughNote.excelReader("G:\\IBC\\org.auto.MercuryTours\\TestScripts\\TestScripts.xls");
		
	}

	private void excelReader(String ExcelPath)throws Exception{
		
		FileInputStream excelFile=new FileInputStream(ExcelPath);
		org.apache.poi.ss.usermodel.Sheet excelSheet;
		org.apache.poi.ss.usermodel.Workbook excelWB;
		org.apache.poi.ss.usermodel.Cell excelCell;
		excelWB=WorkbookFactory.create(excelFile);
		excelSheet=excelWB.getSheet("Login");
		int i=excelSheet.getPhysicalNumberOfRows();
		System.out.println(i);
		excelCell=excelSheet.getRow(0).getCell(0);
		String cellValue=excelCell.getStringCellValue();
		System.out.println(cellValue);
	}
	private void xmlParser(){
		Node eachNode;
		NodeList nodeList;
		NodeList nodeListChild;
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
		
		try{
			DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
			Document doc = icBuilder.parse(System.getProperty("user.dir")+"\\Config.xml");
			doc.getDocumentElement().normalize();
			nodeList=doc.getElementsByTagName("EnvVar");
			for(int iNodeCount=0;iNodeCount<nodeList.getLength();iNodeCount++){
				eachNode=nodeList.item(iNodeCount);
				
				nodeListChild=eachNode.getChildNodes();
				for(int iChildNodeCount=0;iChildNodeCount<nodeListChild.getLength();iChildNodeCount++){
					//objEnvVariables.putIfAbsent(eachNode.getNodeName(), eachNode.getTextContent());
					System.out.println(nodeListChild.item(iChildNodeCount).getNodeType());
				}				
			}
		}catch(Exception e){
			log.error("Error while loading Config.xml");
		}
	}
}
