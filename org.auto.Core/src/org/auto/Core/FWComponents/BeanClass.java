package org.auto.Core.FWComponents;

import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BeanClass {
	
	private static Hashtable<String,String> objEnvVariables=new Hashtable<String,String>();
	private String sModuleName;
	private String sCurrentTCName;
	
	private int iCurrentDataRow;
	private int iCurrentScriptStepRow;
	
	private int iTotalIterCount;
	private int iIterStartRow;
	private int iIterEndRow;
	
	private String sCurrentStepDesc;
	
	/*public BeanClass(){
		loadEnvironmentVariables();
	}*/
	
	public void loadEnvironmentVariables(){
		
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
					if(nodeListChild.item(iChildNodeCount).getNodeType()==1){
						//System.out.println(nodeListChild.item(iChildNodeCount).getNodeName()+"="+nodeListChild.item(iChildNodeCount).getTextContent());
						objEnvVariables.putIfAbsent(nodeListChild.item(iChildNodeCount).getNodeName(), nodeListChild.item(iChildNodeCount).getTextContent());
					}
					
				}				
			}
		}catch(Exception e){
			log.error("Error while loading Config.xml");
		}
		
	}
	
	
	public String getEnvironmentVariable(String EnvVariable){
		
		return objEnvVariables.get(EnvVariable);
	}
	
	public String getsCurrentStepDesc() {
		return sCurrentStepDesc;
	}

	public void setsCurrentStepDesc(String sCurrentStepDesc) {
		this.sCurrentStepDesc = sCurrentStepDesc;
	}

	public int getiTotalIterCount() {
		return iTotalIterCount;
	}

	public void setiTotalIterCount(int iTotalIterCount) {
		this.iTotalIterCount = iTotalIterCount;
	}

	public int getiIterStartRow() {
		return iIterStartRow;
	}

	public void setiIterStartRow(int iIterStartRow) {
		this.iIterStartRow = iIterStartRow;
	}

	public int getiIterEndRow() {
		return iIterEndRow;
	}

	public void setiIterEndRow(int iIterEndRow) {
		this.iIterEndRow = iIterEndRow;
	}

	
	
	public String getsCurrentTCName() {
		return sCurrentTCName;
	}

	public void setsCurrentTCName(String sCurrentTCName) {
		this.sCurrentTCName = sCurrentTCName;
	}
	
	public int getiCurrentScriptStepRow() {
		return iCurrentScriptStepRow;
	}

	public void setiCurrentScriptStepRow(int iCurrentScriptStepRow) {
		this.iCurrentScriptStepRow = iCurrentScriptStepRow;
	}

	public int getiCurrentDataRow() {
		return iCurrentDataRow;
	}

	public void setiCurrentDataRow(int iCurrentTCRow) {
		this.iCurrentDataRow = iCurrentTCRow;
	}

	public String getsModuleName() {
		return sModuleName;
	}

	public void setsModuleName(String sModuleName) {
		this.sModuleName = sModuleName;
	}


}
