package com.setty.automation.reUsableComponents;

import com.setty.automation.driver.Driver;

public class genericActionsLib extends Driver {
// generic actions such as comparing two strings, synchronization methods will be implemented here
	//dataUtilities dataUtils = new dataUtilities();

	  
	public static dataUtilities tcXLS= currentBindingXLS;

	//public static int rowIndex= intBindingRowInd;
	/**
	 * method name: getData
	 * description: method for getting the data from data sheet from particular cell by passing row id and column name
	 * @param ColName
	 * @param RowID
	 * @return : cell value/data
	 * @author Mohammed sujadh khan
	 */

	public String getData(String ColName) {
		String cellData = null;
		cellData = tcXLS.getCellData("Data", ColName, intBindingRowInd );
		//implementation will be provided in milestone 2
		return cellData;
		
	}
	/**
	 * method name: setData
	 * description: method for setting the data to data sheet in particular cell by passing row id and column name
	 * @param ColName
	 * @param RowID
	 * @param:value to be set
	 * @return : none
	 * @author Mohammed sujadh khan
	 */
	public void setData(String ColName, String Value) {
		//implementation will be provided in milestone 2
		tcXLS.setCellData("Data", ColName,intBindingRowInd, Value );
	}
}
