package com.wm.controller.tool;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.wm.model.DataBaseTablesModel;
import com.wm.model.TemplateParamModel;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

@Controller
@RequestMapping(value = "/code")
public class CodeGeneratorController {
	private static final Logger LOGGER = Logger.getLogger(CodeGeneratorController.class);
	
	 @Autowired
	 private ApplicationContext applicationContext;
	/**
	 * 首页
	 * 
	 * @param response
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/code_tool", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView codeTool(HttpServletResponse response,
			HttpServletRequest request, Model model) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", "");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return new ModelAndView("main/admin/tool/code_tool", result);
	}

	/**
	 * 获取数据库信息
	 * @param request
	 * @param response
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = "/create_code", method = { RequestMethod.POST, RequestMethod.GET })
    public Map<String, Object> createCode( HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			 
			ComboPooledDataSource cs=	(ComboPooledDataSource)applicationContext.getBean("dataSource1");
			
			 Connection conn =cs.getConnection();
		 	 DatabaseMetaData dbMetaData = conn.getMetaData();
			ResultSet rs =  dbMetaData.getTables(conn.getCatalog(), "ROOT", null, new String[]{"TABLE"});
			List<DataBaseTablesModel>  list= new ArrayList<DataBaseTablesModel>();
			try {
				while(rs.next()){
					DataBaseTablesModel dbt = new DataBaseTablesModel();
					dbt.setTableName(rs.getString("TABLE_NAME"));
					dbt.setTableRemarks(rs.getString("REMARKS"));//表{}的注释
					
					  //获取列的结果集
					ResultSet rsCol = dbMetaData.getColumns(conn.getCatalog(), "ROOT", dbt.getTableName(),"%");
				    try {
						StringBuffer sb = new StringBuffer();
						StringBuffer sb2 = new StringBuffer();
						while(rsCol.next()){
							sb.append(rsCol.getString("COLUMN_NAME")+",");
							sb2.append(rsCol.getString("TYPE_NAME")+",");
						}
						sb.deleteCharAt(sb.length()-1);
						sb2.deleteCharAt(sb2.length()-1);
						dbt.setFieldName(sb.toString().split(","));
						dbt.setFieldType(sb2.toString().split(","));
					} catch (Exception e) {
						throw  e;
					}finally{
						if(rsCol!=null)
						rsCol.close();
					}
					list.add(dbt);
				}
			} catch (Exception e) {
				throw  e;
			}finally{
				if(rs!=null){
					rs.close();
				}
				if(conn!=null){
					conn.close();
				}
			}
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", list);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}
    
    
    
    @ResponseBody
    @RequestMapping(value = "/generat_code", method = { RequestMethod.POST, RequestMethod.GET })
    public Map<String, Object> generatCode( HttpServletRequest request,@RequestParam("fieldName[]") String[] fieldName,@RequestParam("fieldType[]") String[] fieldType){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			 String codeType= request.getParameter("codeType"); 
			
			 String dataBaseName= request.getParameter("dataBaseName");
			 String dataBaseType= request.getParameter("dataBaseType");
			 String tableName= request.getParameter("tableName");
			 String tableRemarks= request.getParameter("tableRemarks");
			 String fieldRemarks[]=null;
			 
			 
			 ServletContext webcontext = request.getServletContext();
			 TemplateParamModel tpm = new TemplateParamModel();
			 Map<String, Object> param= null;
			 String code =null;
			 if("MapperXml".equals(codeType)){
				 tpm.setClassNamesuffix("Mapper");
//				 tpm.setDataBaseName(dataBaseName);
//				 tpm.setDataBaseType(dataBaseType);
				 tpm.setTableName(tableName);
//				 tpm.setTableRemarks(tableRemarks);
				 tpm.setDbFieldName(fieldName);
//				 tpm.setDbFieldType(fieldType);
				 param= tpm.formatMapperXML();
				 code =  codeGenerator(param, "mapperxml_temp.ftl", webcontext);
				 code = code.replaceAll("<", "&lt;");
				 code = code.replaceAll(">", "&gt;");
			 }else if("MapperJava".equals(codeType)){
				 tpm.setTableName(tableName);
				 param = tpm.formatMapperJava();
				 code =  codeGenerator(param, "mapperjava_temp.ftl", webcontext);
				 code = code.replaceAll("<", "&lt;");
				 code = code.replaceAll(">", "&gt;");
			 }else if("ServiceJava".equals(codeType)){
				 tpm.setTableName(tableName);
				 param = tpm.formatMapperJava();
				 code =  codeGenerator(param, "servicejava_temp.ftl", webcontext);
				 code = code.replaceAll("<", "&lt;");
				 code = code.replaceAll(">", "&gt;");
			 }else if("ServiceImplJava".equals(codeType)){
				 tpm.setTableName(tableName);
				 param = tpm.formatMapperJava();
				 code =  codeGenerator(param, "serviceimpljava_temp.ftl", webcontext);
				 code = code.replaceAll("<", "&lt;");
				 code = code.replaceAll(">", "&gt;");
			 }else if("ControllerJava".equals(codeType)){
				 tpm.setTableName(tableName);
				 param = tpm.formatMapperJava();
				 code =  codeGenerator(param, "controllerjava_temp.ftl", webcontext);
				 code = code.replaceAll("<", "&lt;");
				 code = code.replaceAll(">", "&gt;");
			 }else if("ListJs".equals(codeType)){
				 tpm.setTableName(tableName);
				 param = tpm.formatMapperJava();
//				 code =  codeGenerator(param, "listjs_temp.ftl", webcontext);
//				 code = code.replaceAll("<", "&lt;");
//				 code = code.replaceAll(">", "&gt;");
			 }else if("EntityJava".equals(codeType)){
				// code =  codeGenerator(param, "entityjava_temp.ftl", webcontext);
			 }
			result.put("success", "true");
			result.put("msg", "请求成功");
			result.put("data", code);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "false");
			result.put("msg", "请求失败");
		}
		return result;
	}
    
    
    
    public String codeGenerator(Map<String, Object> param, String tempName,ServletContext webcontext) throws Exception {
    	Configuration configure = new Configuration();
		configure.setDefaultEncoding("utf-8");
		String filePath = null;
		Template template = null;
		Writer out =null;
		FileOutputStream fos=null;
		OutputStreamWriter osw=null;
		
		StringWriter sw =null;
		try {
			 
			String savePath = "E:\\aa\\";//BpmConfig.getInstance().getValue("EndWordFile.path");//文件的保存位置
			String fileUrl = savePath+"/"+UUID.randomUUID().toString()+".xml";
			File filepath=new File(savePath); 	
			filepath.setWritable(true,false);
			if (!filepath.exists()) {				filepath.mkdirs();
			}
////		configure.setClassForTemplateLoading(this.getClass(), tempPath);
			configure.setServletContextForTemplateLoading(webcontext, "/ftl");
			configure.setObjectWrapper(new DefaultObjectWrapper());
			configure.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
			template = configure.getTemplate(tempName, "utf-8");
			File outFile = new File(fileUrl);
//			 fos = new FileOutputStream(outFile);
//			 osw = new OutputStreamWriter(fos, "utf-8");
//			 out = new BufferedWriter(osw);
			 sw = new StringWriter();
			template.process(param, sw);//用fos写入文件
			filePath = fileUrl;
		} catch (Exception e) {
			LOGGER.error("文件【"+tempName+"】生成失败！"+e);
			//throw new Exception("文件【"+tempName+"】生成失败！"+e);
		}finally{
			if(fos!=null){
				fos.close();
			}
			if(osw!=null){
				osw.close();
			}
			if(out!=null){
				out.close();
			}
			if(sw!=null){
				sw.close();
			}
		}
		return sw.toString();

	}
}
