# MyFramework

Introduction

This document helps to understand and to evaluate the framework created for the purpose of “Test Automation Assignment” assignment that was assigned to  Chandrasekar S on  14/03/2018. It will contain details that are specific to the framework such as tools,  language used, approach followed, framework model, design decisions etc.

Tools & Language Used

The assignment involves designing and developing a framework to automate steps to be performed in a web application, using Selenium as the tool of choice. Even though selenium supports different languages such as Java, Python, Ruby etc., Java is the language of choice as it has a wide user base and is quite popular. 

TestNG or Junit?

Neither. Even though both TestNG and Junit provide a robust base to develop on, most applications can do with a simpler and a customized framework, even though such an approach will require some effort initially. Having said that, there are open source frameworks available which are more user-friendly, when compared to TestNG or Junit or both, and can support multiple technologies.

Framework Used

The framework that has been developed for the purpose of this assignment is a hybrid framework: combination of modular, data-driven, and keyword-driven. 

Two major aspects of a framework that most architects overlook is the ease of maintenance and the end users. The scripts and object repositories should be easy to maintain by users who are new to the framework and are not technically savvy. This ensures that any change in application can be seamlessly incorporated into the test scripts. This is a major advantage in using keywords, not only to denote different actions performed in appliction but also to describe the application fields. 

A high degree of modularity increases reusability and decreases the effort required to maintain the framework. A data-driven approach ensures that the functionalities can be tested using different combinations of data and using huge volumes of data, something that isn't possible manually.
Browser(s)

Google Chrome has been used for the purpose of this framework as there is some incompatibility between recent versions of firefox and latest version of selenium. The framework can be modified to accomodate other popular browser such as Internet Explorer, Opera, and Safari.

Folder Structure

Files that belong to the framework are inside the folder named “org.auto.Core”, as shown below:


Class files where framework is implemented are available as shown below:


The “org.auto.Core” folder is an “Eclipse” project. It will be converted to a “JAR” (Java Archive) file that can be used by scripts. The important thing to note here is that the framework is generic and hence, can be used for any web application.

Implementation

As mentioned in the previous section, the framework files will be packaged into a “JAR” file and this file will be used by scripts of different applications. For example, Amazon is the web site that is used in this assignment and hence a folder structure/eclipse is created, as shown below. Lets refer to this as the “Project Folder”:



Object Repository

Instead of following the typical page object model, this framework invloves capturing all application fields in an excel spreadsheet and assigning a logical name to them. This sheet is named as “OR.xls” and is available inside the “Object Repository” folder that resides inside “Project Folder”.

Currently, objects in different pages of application are grouped together in one single sheet. However, the framework can be extended to replicate the page object model, where in, objects in different pages of application can be in different sheet, each sheet denoting an application page.

The main advantage of such an approach is that, in the event that the value of locators changes, which is the case most often, these changes can be done in the excel. There will be no need to update the code base, which is the case with conventional page object model. Hence, anyone with the knowledge of using excel and of HTML can easily make the changes.

Test Scripts

As the framework uses keywords to define actions, no programming knowledge is required to develop test scripts. Instead, the test steps are captured as keywords in an excel. This excel - “TestScripts.xls” resides in “Test Scripts”, inside the “Project Folder”.
Again, the biggest advantage here is that any update done to the test cases can be easily incroporated into the test scripts, without the need of knowing the language in which the framework is developed. The framework could be implemented either in Java or in Python and that does not really affect the way in scripts are developed.

Testers can refer to a glossary of keywords to get a good understanding of how to use them effectively. Such an approach will lead to lead to faster script development and reduced maintenance time.

Test Data

Test Data can be provided in two ways:

Hard coded as part of the test scripts
As a seperate and stand alone data repository

The first approach ensures that static data can be used along with the keywords. Even though this data is expected to remain static, any change can be implemented easily as the data resides in an excel workbook.

The second approach provides teams with the flexibility of accomodating dynamic data and of including iterations. Any data that has to be changed everytime a script is executed can be easily maintained in the central repository. Also, the scripts can be executed for different data sets, as all the user has to do is include an extra row in this excel workbook and the script will be executed for that new row.

This central data repository is available in “TestData.xls”, residing inside “Test Data” folder of “Project Folder”

Dependencies

As the framework has been implemented in Java, there are some dependencies of external “JAR” files. These files are available in “JARs” folder that is inside the “Project Folder”

Execution

1. Unzip the file “MyFramework.zip” to desired location.
2. Alternatively, clone the Git repository using link https://github.com/reachcsekar/MyFramework.git
3. There will two folders
4. org.auto.core – Framework code
5. org.auto.amazon – Implemntation of framework
6. Open the “org.auto.amazon” folder and copy the “JARs” folder to your “C:” driver. This is need for the script to execute succesfully.
7. Create a workspace in “Eclipse” and import “org.auto.amazon” as a project.
8. In eclipse, navigate to org.aut.amazon\src\org.auto.Driver path using Package/Project Explorer. 
9. Open “DriverScript.Java” file. Right-click the file in Package Explorer and select Run As -> Java Application in the menu that pops up.

Pre-requisites

Ensure Java is installed in system. If not, download and install from https://java.com/en/download/
Ensure “Google Chrome” browser is installed in system. If not, download and install it from https://www.google.co.in/chrome/index.html
Download and install “Eclipse”.

Reports

Execution results are captured and saved to “Reports” folder, that resides inside “Project Folder” in HTML format. The folder contains two sample reports for reference.
