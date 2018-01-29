package com.wm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 代码生成数据模型
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2017.12 
 */
public class TemplateParamModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8821334010087386446L;

	private String dataBaseName;
	private String dataBaseType;

	private String tableName;
	private String tableRemarks;
	private String[] dbFieldName;
	private String[] dbFieldType;
	private String[] dbFieldRemarks;

	private String className;
	private String classRemarks;
	private String[] classFieldName;
	private String[] classFieldType;
	private String[] classFieldRemarks;

	private String classNamesuffix;

	public String getClassNamesuffix() {
		return classNamesuffix;
	}

	public void setClassNamesuffix(String classNamesuffix) {
		this.classNamesuffix = classNamesuffix;
	}

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
		this.tableName = tableName.toUpperCase();
		this.setClassName(tableName);
	}

	public String getTableRemarks() {
		return tableRemarks;
	}

	public void setTableRemarks(String tableRemarks) {
		this.tableRemarks = tableRemarks;
		this.setClassRemarks(tableRemarks);
	}

	public String[] getDbFieldName() {
		return dbFieldName;
	}

	public void setDbFieldName(String[] dbFieldName) {
		this.dbFieldName = dbFieldName;
		for (int i = 0; i < dbFieldName.length; i++) {
			dbFieldName[i]=dbFieldName[i].toUpperCase();
		}
		this.setClassFieldName(dbFieldName.clone());
	}

	public String[] getDbFieldType() {
		return dbFieldType;
	}

	public void setDbFieldType(String[] dbFieldType) {
		this.dbFieldType = dbFieldType;
		this.setClassFieldType(dbFieldType.clone());
	}

	public String[] getDbFieldRemarks() {
		return dbFieldRemarks;
	}

	public void setDbFieldRemarks(String[] dbFieldRemarks) {
		this.dbFieldRemarks = dbFieldRemarks;
		this.setClassFieldRemarks(dbFieldRemarks.clone());
	}

	public String getClassName() {
		return className;
	}

	private void setClassName(String className) {
		this.className = this.classStyle(className);
	}

	public String getClassRemarks() {
		return classRemarks;
	}

	private void setClassRemarks(String classRemarks) {
		this.classRemarks = classRemarks;
	}

	public String[] getClassFieldName() {
		return classFieldName;
	}

	private void setClassFieldName(String[] classFieldName) {
		for (int i = 0; i < classFieldName.length; i++) {
			classFieldName[i] = this.humpStyle(classFieldName[i]);
		}
		this.classFieldName = classFieldName;
	}

	public String[] getClassFieldType() {
		return classFieldType;
	}

	private void setClassFieldType(String[] classFieldType) {

		for (int i = 0; i < classFieldType.length; i++) {
			classFieldType[i] = this.sqlToJavaType(classFieldType[i]);
		}

		this.classFieldType = classFieldType;
	}

	public String[] getClassFieldRemarks() {
		return classFieldRemarks;
	}

	private void setClassFieldRemarks(String[] classFieldRemarks) {
		this.classFieldRemarks = classFieldRemarks;
	}

	private String classStyle(String str) {
		str = str.toLowerCase();
		String[] arr = str.split("_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			char[] c = arr[i].toCharArray();
			if (c[0] >= 97 && c[0] <= 122) {
				c[0] = (char) (c[0] - 32);
			}
			sb.append(c);
		}
		return sb.toString();
	}

	private String humpStyle(String str) {
		str = str.toLowerCase();
		String[] arr = str.split("_");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			char[] c = arr[i].toCharArray();
			if (i > 0) {
				if (c[0] >= 97 && c[0] <= 122) {
					c[0] = (char) (c[0] - 32);
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}

	private String sqlToJavaType(String str) {
		// http://blog.csdn.net/anxpp/article/details/51284106
		if ("VARCHAR".equalsIgnoreCase(str) || "CHAR".equalsIgnoreCase(str)) {
			return "String";
		} else if ("INT".equalsIgnoreCase(str)
				|| "INTEGER".equalsIgnoreCase(str)
				|| "MEDIUMINT".equalsIgnoreCase(str)
				|| "SMALLINT".equalsIgnoreCase(str)
				|| "TINYINT".equalsIgnoreCase(str)) {
			return "int";
		} else if ("BIGINT".equalsIgnoreCase(str)) {
			return "long";
		} else if ("DECIMAL".equalsIgnoreCase(str)
				|| "NUMERIC".equalsIgnoreCase(str)
				|| "DOUBLE".equalsIgnoreCase(str)
				|| "REAL".equalsIgnoreCase(str)) {
			return "double";
		} else if ("FLOAT".equalsIgnoreCase(str)) {
			return "float";
		} else if ("DATA".equalsIgnoreCase(str) || "TIME".equalsIgnoreCase(str)
				|| "DATETIME".equalsIgnoreCase(str)
				|| "TIMESTAMP".equalsIgnoreCase(str)
				|| "YEAR".equalsIgnoreCase(str)) {

			return "Date";
		}

		return null;
	}

	public Map<String, Object> formatMapperJava() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("tableName", this.tableName);
		res.put("entityName", this.className);
		res.put("humpEntityName", humpStyle(this.tableName));
		return res;
	}
	
	public Map<String, Object> formatMapperXML() {
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("tableName", this.tableName);
		res.put("targetName", this.className + this.classNamesuffix);
		res.put("entityName", this.className);
		res.put("humpEntityName", humpStyle(this.tableName));
		
		if (classFieldName == null || dbFieldName == null
				|| classFieldName.length != dbFieldName.length)
			return null;
		List<String> prop = new ArrayList<String>();
		List<String> whereConditions = new ArrayList<String>();
		List<String> setValues = new ArrayList<String>();
		
		for (int i = 0; i < classFieldName.length; i++) {
			String cfn=classFieldName[i];
			String dfn=dbFieldName[i];
			if (cfn == "id") {// 根据“是否主键”判断，（待改进）
				prop.add("<id property=\"id\" column=\"ID\" />");
			} else {
				prop.add("<result property=\""+cfn+"\" column=\""+dfn+"\" />");
			}
			whereConditions.add("<if test=\""+cfn+" != null\"> \r\n\t\t\t\tAND "+dfn+" = #{"+cfn+"} \r\n\t\t\t</if>");
			setValues.add("<if test=\""+cfn+" != null\"> \r\n\t\t\t\t"+dfn+" = #{"+cfn+"}, \r\n\t\t\t</if>");
		}
		res.put("prop", prop);
		res.put("tableFields", arrToStr(dbFieldName));
		res.put("whereConditions", whereConditions);
		res.put("setValues", setValues);
		res.put("insertValues", insertVal(classFieldName));
		res.put("whereConditions", whereConditions);
		res.put("id", "#{id}");
		
		return res;
	}
	
	public String arrToStr(String[] arr){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i].toUpperCase()+",");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	public String insertVal(String[] arr){
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append("#{"+arr[i]+"},");
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
