package com.sa.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sa.to.SurveyDataTO;

public class SurveyDataTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int COL_RESULT_ID=0;
	public static final int COL_POSTCODE=1;
	public static final int COL_SALES=2;
	
	private final String[] columnNames={"Result Id","Postcode","Sales"};
	
	private SurveyDataTO[] results=null;
	
	public SurveyDataTableModel(){
	
	}
	
	public SurveyDataTableModel(SurveyDataTO[] results){
		this.setSurveyResults(results);
	}
	
	public SurveyDataTableModel(List<SurveyDataTO> resultList){
		this.setSurveyResults(resultList);
	}
	
	public void setSurveyResults(SurveyDataTO[] results){
		this.results=results;
		this.fireTableDataChanged();
	}
	
	public void setSurveyResults(List<SurveyDataTO> resultList){
		results=resultList.toArray(new SurveyDataTO[0]);
		this.fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		int rowCount=0;
		if(results!=null){
			rowCount=results.length;
		}
		return rowCount;
	}

	@Override
	public Object getValueAt(int row, int col) {
		SurveyDataTO result=results[row];
		return this.getColumnValue(result, col);
	}
	
	public String getColumnName(int col){
		return columnNames[col];
	}
	
	private Object getColumnValue(SurveyDataTO result,int col){
		Object value=null;
		switch(col){
			case SurveyDataTableModel.COL_RESULT_ID :
				value=result.getResultId();
				break;
			case SurveyDataTableModel.COL_POSTCODE :
				value=result.getPostcode();
				break;
			case SurveyDataTableModel.COL_SALES :
				value=result.getSales();
				break;
		}
		return value;
	}

}
