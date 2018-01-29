package com.wm.model;

import java.io.Serializable;
/**
 * 数据库信息封装bean
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2017.12 
 */
public class DataBaseTablesModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4780447489897072456L;

	private String dataBaseName;
	private String dataBaseType;
	private String tableName;
	private String tableRemarks;
	private String[] fieldName;
	private String[] fieldType;
	private String[] fieldRemarks;

	public String getDataBaseName() {
		return dataBaseName;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public String getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableRemarks() {
		return tableRemarks;
	}

	public void setTableRemarks(String tableRemarks) {
		this.tableRemarks = tableRemarks;
	}

	public String[] getFieldName() {
		return fieldName;
	}

	public void setFieldName(String[] fieldName) {
		this.fieldName = fieldName;
	}

	public String[] getFieldType() {
		return fieldType;
	}

	public void setFieldType(String[] fieldType) {
		this.fieldType = fieldType;
	}

	public String[] getFieldRemarks() {
		return fieldRemarks;
	}

	public void setFieldRemarks(String[] fieldRemarks) {
		this.fieldRemarks = fieldRemarks;
	}

}
