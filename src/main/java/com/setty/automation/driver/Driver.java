package com.setty.automation.driver;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.setty.automation.reUsableComponents.Reflections;
import com.setty.automation.reUsableComponents.dataUtilities;
import com.setty.automation.reUsableComponents.genericActionsLib;




public class Driver {
	//just sample basic main class, complete implementation will be available in mile stone 2 delivery
	public static WebDriver driver;
	public static Properties prop;
	public static dataUtilities suiteXLS;
	public static dataUtilities currentBindingXLS;
	public static String strRMSheetName;
	public static String strRunManagerTestCase;
	public static String strBindingStepTestCase;
	public static String strBindingDataTestCase;
	public static String strExecutableKeyword;
	public static int intBindingRowInd;
	public static ExtentReports extent;
	public static String strReportDirPath;
	public static String strReport_path;
	public static String strExecutionMode;
	public static String strTestStatus;
	public static int intRMIndex;
	public static String strTestSuite;
	public static ExtentTest logger;
	
	LogStatus status;
	
	private static ArrayList<ArrayList> arrList=new ArrayList<ArrayList>();
	static int x=0;
	/**
	 * static block for loading application properties
	 */
	static
	{
		try
		{
			prop=new Properties();
			FileInputStream ip= new FileInputStream(System.getProperty("user.dir") +"//src//main//resources//configFiles//FrameworkProperties.properties");
			prop.load(ip);
			System.out.println(prop.getProperty("ApplicationURL"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * method name : startup
	 * description: method for initializing the framework components
	 * @author Mohammed sujadh khan
	 * @return:null
	 */
	public static void startup()
	{
		try
		{
			if(strTestSuite.equalsIgnoreCase("0"))
			{
				strRMSheetName=prop.getProperty("RUNMANAGER_SHEET");
			}
			else
			{
				strRMSheetName=strTestSuite;
			}
			
			String strRunManager_Path=System.getProperty("user.dir")+ "//src//main//resources//RunManager.xlsx";
			suiteXLS= new dataUtilities(strRunManager_Path);
			String strCurrentTimeStamp=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
			strReportDirPath=prop.getProperty("ReportDirectoryPath")+"\\"+strRMSheetName+"_"+strCurrentTimeStamp;
			File res=new File(strReportDirPath);
			res.mkdirs();
			strReport_path=strReportDirPath+File.separator+strRMSheetName+"_"+strCurrentTimeStamp+".html";
			extent=new ExtentReports(strReport_path);
		}
		catch(Exception e)
		{
			System.err.println("Error observed in initialization method "+ e.getMessage());
			logger.log(LogStatus.FAIL, "Error observed in initialization method "+ e.getMessage());
			e.printStackTrace();
			end();
			
		}
	}
	/**
	 * method name:main
	 * description: framework main method
	 * @param args (optional)
	 * @throws Exception
	 * @author Mohammed sujadh khan
	 */
	public static void main(String... args) throws Exception
	{
		try {
		String[] arrExecMode=null;
		if(args.length>0)
		{
			arrExecMode=args[0].split(":");
		}
		else
		{
			arrExecMode="0:0".split(":");
		}
		strExecutionMode=arrExecMode[0];
		strTestSuite=arrExecMode[1];
		startup();
		if(strExecutionMode.equalsIgnoreCase("0"))
		{
			selectCasesInSuiteRunMode();
			if(arrList.size()<=0)
			{
				throw new Exception("No Test Cases were selected for execution");
			}
		}
		else
		{
			//selectCasesonQCID();
			if(arrList.size()<=0)
			{
				throw new Exception("No Test Cases were selected for execution");
			}
		}
		
		System.out.println(arrList.size());
		
		Iterator i=arrList.iterator();
		while(i.hasNext())
		{
			logger=null;
			exec((ArrayList<?>) i.next());
		}
		//calling end method post keywords execution
		end();
		
		//ZipUtility.zipFile();
		if(prop.get("LaunchHTMLReport").equals("Yes"))
		{
			Desktop.getDesktop().open(new File(strReport_path));
		}
	}
		catch(Exception e)
		{
			System.out.println("error observed in main method "+e.toString());
		}
	}
	/**
	 * method name :startUp
	 * description : method for initializing the framework and verifying the configurations
	 * @param:none
	 * @author Mohammed sujadh khan
	 */
	public static void startUp() {
		//implementation will be in milestone 2
	}
	/**
	 * method name: selectCasesInSuiteRunMode
	 * Description: Method for selecting test cases marked as Yes in run manager
	 * @param:none
	 * @author Mohammed sujadh khan
	 */
	private static void selectCasesInSuiteRunMode()
	{
		try
		{
			for(int intRMRowIndex=1; intRMRowIndex <= suiteXLS.getRowCount(strRMSheetName); intRMRowIndex++)
			{
				strRunManagerTestCase=suiteXLS.getCellData(strRMSheetName,"TestCase",intRMRowIndex);
				if(suiteXLS.getCellData(strRMSheetName,"Run",intRMRowIndex).equalsIgnoreCase("Yes"))
				{
					System.out.println(suiteXLS.getCellData(strRMSheetName,"Run",intRMRowIndex));
					suiteXLS.setCellData(strRMSheetName,"Status", intRMRowIndex,"");
					suiteXLS.setCellData(strRMSheetName,"ResultPath",intRMRowIndex,"");
					currentBindingXLS=new dataUtilities(System.getProperty("user.dir") + "//src//main//resources//dataSuites//" +strRMSheetName + ".xlsx");
					for(int intBindingStepRowIndex=1;intBindingStepRowIndex<=currentBindingXLS.getRowCount("keywords"); intBindingStepRowIndex++)
					{
						strBindingStepTestCase=currentBindingXLS.getCellData("keywords","TestCase", intBindingStepRowIndex);
						System.out.println(strBindingStepTestCase);
						if(strRunManagerTestCase.equals(strBindingStepTestCase))
						{
							for(int intBindingDataRowIndex=1; intBindingDataRowIndex<=currentBindingXLS.getRowCount("Data");intBindingDataRowIndex++)
							{
								strBindingDataTestCase=currentBindingXLS.getCellData("Data","TestCase",intBindingDataRowIndex);
								if(strRunManagerTestCase.equals(strBindingDataTestCase) && strBindingStepTestCase.equals(strBindingDataTestCase))
								{
									ArrayList<String> al=new ArrayList<String>();
									al.add(0,Integer.toString(intRMRowIndex));
									al.add(1, Integer.toString(intBindingDataRowIndex));
									al.add(2, currentBindingXLS.getCellData("keywords", 1, intBindingStepRowIndex));
									for(int strBindingStepTestCase_colIndex=2, n=3; strBindingStepTestCase_colIndex<currentBindingXLS.getColumnCount("keywords", intBindingDataRowIndex); strBindingStepTestCase_colIndex++,n++)
									{
										strExecutableKeyword=currentBindingXLS.getCellData("keywords",
												strBindingStepTestCase_colIndex, intBindingStepRowIndex);
										if(strExecutableKeyword.length()!=0)
										{
											al.add(n,strExecutableKeyword);
										}
									}
									System.out.println("al:"+al);
									arrList.add(x,al);
									x++;
								}
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			System.err.println("error observed in selectcaseinsuitemode method " +e.getMessage());
			logger.log(LogStatus.FAIL, "error observed in selectcaseinsuitemode method " +e.getMessage());
			e.printStackTrace();
			
		}
	}
	/**
	 * method name : exec
	 * description:method for executing the keywords fetched from keywords, regression sheet
	 * @param allList
	 * @author Mohammed sujadh khan
	 */
	private static void exec(ArrayList<?> allList) throws Exception
	{
		boolean exceptionOccured = false;
		try
		{
			System.out.println(allList.get(3));
			intRMIndex=(Integer.parseInt((String) allList.get(0)));
			intBindingRowInd=(Integer.parseInt((String) allList.get(1)));
			System.out.println(intBindingRowInd);
			String strTestCaseName=(String)allList.get(2);
			System.out.println(strTestCaseName);
			logger=extent.startTest(strTestCaseName);
			if(allList.get(3).equals("Chrome") || allList.get(3).toString().length()==0)
			{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +"//src//main//resources//drivers//chromedriver.exe");
				driver=new ChromeDriver();
				
				System.out.println("Size:  "+allList.size());
			}
			else if(allList.get(3).equals("FireFox"))
			{
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"\\geckodriver.exe");
				driver=new FirefoxDriver();
				
			}
			else if(allList.get(3).equals("IE"))
			{
				DesiredCapabilities capabilities=DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capabilities.setCapability("requiredWindowFocus", true);
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") +"\\IEDriverServer.exe");
				//driver.new InternetExplorerDriver(capabilities);
			}
			driver.manage().window().maximize();
			for(int i=4;i< allList.size();i++)
			{
				System.out.println("keyy:: "+allList.get(i));
				boolean blnReflectionResult=Reflections.reflect((String) allList.get(i));
				if(blnReflectionResult == false)
				{
					driver.quit();
					exceptionOccured = true;
					break;
				}
			}
			
			LogStatus status=logger.getRunStatus();
			
			intBindingRowInd=0;
			if(status.toString().equalsIgnoreCase("pass"))
			{
				strTestStatus="passed";
			}
			else
			{
				strTestStatus="failed";
			}
			//strAlmTestResultPath=strReporttDirPath;
			if (exceptionOccured==false) {
				driver.close();
			}
			suiteXLS.setCellData(strRMSheetName,"Status", intRMIndex, strTestStatus);
			suiteXLS.setCellData(strRMSheetName,"ResultPath", intRMIndex,strReportDirPath );
		}
		catch(Exception e)
		{
			logger.log(LogStatus.FAIL, "error observed in execute method" + e.getMessage());
			e.printStackTrace();
			System.out.println("Test Case at binding row index "+ allList +"Could not be executed "+ e.getMessage());
			
		}
		
	}
/**
 * Name: end
 * description: Method for ending the test case run
 * @author Mohammed sujadh khan
 */
	public static void end() {
		
		try {
			extent.endTest(logger);
			driver.quit();
			extent.flush();
		}
		catch (Exception e) {
			System.err.println("Error observed in end test method" + e.getMessage());
			e.printStackTrace();
		}
		
	}
}
