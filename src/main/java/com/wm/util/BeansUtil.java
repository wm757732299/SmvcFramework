package com.wm.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BeansUtil {

	public Map<String, String> objToMap(Object obj, Class<?> clazz) {
		Map<String, String> map = null;
		try {
			if (obj == null)
				return null;
			map = new HashMap<String, String>();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				boolean accessFlag = field.isAccessible();
				field.setAccessible(true);
				map.put(field.getName(), field.get(obj) + "");
				field.setAccessible(accessFlag);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return map;
	}

	public Object mapToObj(Map<Object, Object> map, Class<?> clazz) {
		Object obj = null;
		if (map == null)
			return null;
		try {
			obj = clazz.newInstance();
			boolean flag = false;
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
					continue;
				}
				flag = field.isAccessible();
				field.setAccessible(true);
				if(field.getType()==java.lang.Integer.class||field.getType()==int.class){
					field.set(obj, Integer.valueOf(map.get(field.getName()).toString()));
				}else if(field.getType()==java.util.Date.class){
					Date d = new Date(map.get(field.getName()).toString());
					field.set(obj,  d);
				}else if(field.getType()==java.lang.String.class){
					field.set(obj, map.get(field.getName()));
				}
				
				field.setAccessible(flag);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return obj;
	}
//	 static void change(String str){
//	        str="welcome";
//	        
//	        
//	    }
//	 static void change2(SysMenu sysMenu){
//		 sysMenu.setId("TRTR");
//	    }
//	     
//	    public static void main(String[] args) {
//	        String str = "123";
//	        String str2= new String ("RRRR");
//	        
//	        SysMenu sysMenu =new SysMenu();
//	        sysMenu.setId("RRRRRR");
//	        change(str2);
//	        
//	        change2(sysMenu);
//	        System.out.println(sysMenu.getId());
//	    } 
}
