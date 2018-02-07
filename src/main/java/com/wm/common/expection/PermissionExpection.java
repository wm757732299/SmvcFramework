package com.wm.common.expection;

/**
 * 操作权限验证异常
 * 
 * @version 1.0
 * @author WangMingM
 * @date 2018.02.01
 *
 */
public class PermissionExpection extends RuntimeException {

	private static final long serialVersionUID = 5552798801623367634L;
	
	
	public PermissionExpection(){
		super();
	};
	public PermissionExpection(String arg0){
		super(arg0);
	};
}
