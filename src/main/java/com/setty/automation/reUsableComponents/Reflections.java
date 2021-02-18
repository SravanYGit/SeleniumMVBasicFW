package com.setty.automation.reUsableComponents;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.setty.automation.driver.Driver;

public class Reflections {
	/**
	 * method name:reflect
	 * description:method for invoking keywords for execution
	 * @param methodName
	 * @return :boolean 
	 * @author Mohammed sujadh khan
	 */

	public static boolean reflect(String methodName) throws Exception
	{
		boolean isMethodExecuted;
		ExtentTest logger=Driver.logger;
		try
		{
			String path1="com.setty.automation.businessComponents";
			String relPath=path1.replace(".", "/");
			URL resource=Thread.currentThread().getContextClassLoader().getResource(relPath);
			if(resource==null)
			{
				throw new RuntimeException("Unexpected Problem: No resource for "+relPath);
			}
			//System.out.println(resource.getPath());
			String updatedPath = resource.getPath().replace("%20", " ");
			File f= new File(updatedPath);
			//System.out.println(f.getName());
			
			String[] files=f.list();
			if (files == null) {
				System.out.println("f list is empty, issue with the path");
				logger.log(LogStatus.FAIL, "issue with the project path ");
			}
			Boolean isMethodPresent = false;
			
			for(int i=0;i<files.length; i++)
			{
				String fileName=files[i];
				String className=null;
				String fileNm=null;
				if(fileName.endsWith(".class"))
				{
					fileNm=fileName.substring(0, fileName.length() - 6);
					className = path1+ "." +fileNm;
					//System.out.println("classname :"+className);
				}
				if(className!=null)
				{
					Class<?> c=Class.forName(className);
					Method[] m1=c.getDeclaredMethods();
					for(int j=0;j<m1.length;j++)
					{
						String methodFullName=m1[j].toString();
						String[] names=methodFullName.split("\\(");
						String name1=names[0];
						String method_Name=name1.substring(name1.lastIndexOf('.')+1);
						String ExactMethodName=method_Name.replaceAll("[()]", "");
						if(ExactMethodName.equals(methodName))
						{
							isMethodPresent = true;
							System.out.println("Method Under Execution :"+methodName);
							
							Object o=c.newInstance();
							//Class[] parameterTypes = new Class[] {WebDriver.class, Integer.class, ExtentTest.class};
							//Object[] arguments = new Object[] {driver, dataRowIndex, logger};
							
							//Class[] parameterTypes = new Class[] {};
							//Object[] arguments = new Object[] {};
							
							//Method mx=o.getClass().getDeclaredMethod(ExactMethodName, parameterTypes);
							Method mx=o.getClass().getDeclaredMethod(ExactMethodName);
							mx.invoke(o);
							isMethodExecuted = true;
							return isMethodExecuted;
							
						}
					}
				}
			}
		
			if(isMethodPresent == false)
			{
				logger.log(LogStatus.FAIL, "method :" + methodName + " not found in business components");
				LogStatus stat=logger.getRunStatus();
				return false;
			}
		}
		
		catch(Exception e)
		{
			System.err.println("error observed during method exeution :"+methodName  +e.getMessage()) ;
			logger.log(LogStatus.ERROR, "error observed while executing keyword " + methodName + e.toString());
			return false;
		}
		return false;
	}
}
