package org.auto.Core.FWComponents;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;




public class Reporter {

	
	private static BufferedWriter bufferedWriter;
	private static int iStepCount = 1;
	
	
	public static void setReportFile() throws IOException{
		
		File reportTextFile;
		String sReportFolderPath;
		
		//check whether a text file with specified name already exists in the path. If exists, then delete it
		sReportFolderPath=System.getProperty("user.dir")+"\\Reports";
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		timeStamp=timeStamp.replace(".","-");
		reportTextFile  = new File(sReportFolderPath + "//Report-"+timeStamp+".html");
		if (reportTextFile.exists()){
			reportTextFile.delete();
		}
		//Create new file
		reportTextFile.createNewFile();
		//Setting up instance to write to newly created file
		bufferedWriter = new BufferedWriter(new FileWriter(reportTextFile));
		bufferedWriter.write("<html>");
		bufferedWriter.write("<title>Automation Report</title>");
		bufferedWriter.write("<h1>Detailed Results</h1>");
		bufferedWriter.write("<body>");
		bufferedWriter.write("<table border = \"3\" cellspacing = \"2\" cellpadding = \"15%\">");
		bufferedWriter.write("<CAPTION><h3>RUN DETAILS</h3></CAPTION>");
		bufferedWriter.write("<TR><TH>S.No.</TH><TH>Test Case</TH><TH>Step Description</TH><TH>Exepcted</TH><TH>Actual</TH><TH>Result</TH>");
		
		
	}
	
	public static void ReportStep(String TestCaseName, String StepDesc, String ExpectedRes, String ActualRes, String StepStatus) throws IOException{
		String sResultString="";
		
		switch (StepStatus){
		case "PASS":
			sResultString = "<TD><font color = \"green\"><B>PASSED</B></font></TD>";
			break;
		case "FAIL":
			sResultString = "<TD><font color = \"red\"><B>FAILED</B></font></TD>";
			break;		
		}
		//Writing result to text file
		bufferedWriter.write("<TR><TD>"+iStepCount+"</TD><TD>"+TestCaseName+"</TD><TD>"+StepDesc+"</TD><TD>"+ExpectedRes+"</TD><TD>"+ActualRes+"</TD>"+sResultString+"</TR>");
		iStepCount+=1;
		
	}
	
	public static void generateFinalReport() throws IOException{
		bufferedWriter.write("</body>");
		bufferedWriter.write("</html>");
		bufferedWriter.flush();
		bufferedWriter.close();
	}
	
}
