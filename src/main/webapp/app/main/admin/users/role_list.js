/**
 * 角色管理
 * @author:WangMingM
 * @date:2017.11.30
 */
$(function() {
	RoleList.init();
});

var RoleList = function() {
	'use strict';
	var _cols = [ {
		width : 80,
		colName : 'roleName',
		text : '角色',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 20,
		colName : 'roleLevel',
		text : '级别',
		type : 'string',
		flex : false,
		colClass : 'text-center',
		formatter : [ function(v) {
			if(0==v){
				return "系统";
			}else if(1==v){
				return "一级";
			}else if(2==v){
				return "二级";
			}else if(3==v){
				return "三级";
			}else if(4==v){
				return "四级";
			}else if(5==v){
				return "五级";
			}else if(6==v){
				return "六级";
			}else {
				return "--";
			}
		}]
	}, {
		width : 100,
		colName : 'remarks',
		text : '备注',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 50,
		colName : 'timeStamp',
		text : '时间',
		type : 'date',
		flex : false,
		colClass : 'text-center',
		sort : 'down',
		formatter:[function (v,i){
			return moment(v).format("YYYY-MM-DD HH:mm");
		}]
	}, {
		width : 100,
		colName : '_operation',
		text : '操作',
		type : 'string',
		flex : false,
		colClass : 'text-center',
		formatter : [ function roleName (v,i) {
			var html= '<button name ="authorityBtn" class="btn btn-mini btn-warning" onclick="javascript:RoleList.authority(\''+i+'\',\''+v+'\')" style="margin-right: 20px;"><i class="icon icon-key">权限</i></button>'
					 +'<button name ="authorizeBtn" class="btn btn-mini btn-success" onclick="javascript:RoleList.authorize(\''+i+'\',\''+v+'\')" style="margin-right: 20px;"><i class="icon icon-hand-right">授权</i></button>'
					 +'<button name ="editRole" class="btn btn-mini btn-info"  onclick="javascript:RoleList.editRoleWindow(\''+i+'\')" style="margin-right: 20px;"><i class="icon icon-edit"></i>编辑</button>'
					 +'<button name ="delRole" class="btn btn-mini btn-danger" onclick="javascript:RoleList.delRole(\''+i+'\')"><i class="icon icon-trash" >删除</i></button>';
			return html;

		}]
	} ];
	
	var _url = basePath + "/sysrole/role_list_data.wmctl";
	var _param=null;
	var _data = null;
	var _loginRoleLevel=null;
	// =========================定义方法===============================//
	var _searchData = function(cols, url) {
		AjaxRequest.asyncAjax(url, _param, function(result) {
			var record = result.data;
			_loginRoleLevel =result.loginRoleLevel;
			_data = DataModel.buildDataModel(cols, record);
			$('table.datatable').datatable({
				checkable : true,
				checkByClickRow : false,
				sortable : true,
				data : _data
			});
			PageTool.createPageBar(result.pageInfo);
		});
	};

	var _reLoadData = function(param) {
		AjaxRequest.asyncAjax(_url, param, function(result) {
			var record = result.data;
			_data = DataModel.buildDataModel(_cols, record);
			$('table.datatable').datatable('load', _data);
			PageTool.createPageBar(result.pageInfo);
		});
	};

	var _theAddRoleWindow = null;
	var _addRoleWindow = function() {
		var addHmtl = RoleAdd.initSelect(_loginRoleLevel);
		_theAddRoleWindow = Dialog.dialog({
			backdrop : 'static',
			toggle : 'modal',
			height : '400px',
			width : '700px',
			title : '添加',
			moveable : true,
			custom : function() {
				return addHmtl;
			}
		});
		_theAddRoleWindow.show();
		document.getElementById("saveBtn").onclick=_addSaveData;
	};

	var _theEditRoleWindow = null;
	var _editRoleWindow = function(id) {
		var addHmtl = RoleAdd.initSelect(_loginRoleLevel);//getAddRoleHtml();
		var ediUrl = basePath + '/sysrole/edit_role.wmctl';
		var data ={id:id};
		AjaxRequest.asyncAjaxPost(ediUrl, data, function(result) {
			if (result.success == "true") {
				_theEditRoleWindow = Dialog.dialog({
					backdrop : 'static',
					toggle : 'modal',
					height : '400px',
					width : '700px',
					title : '编辑',
					moveable : true ,
					custom :function() {
						return addHmtl;
					}
				});
				var formData =result.data;
				//==填充数据并展示编辑窗口==//
				_theEditRoleWindow.show({onShow: function() {
					for ( var i in formData) {
						if(document.getElementById(i)){
							if("roleLevel"==i){
								var optArr =document.getElementById(i).options;
								for (var j = 0; j < optArr.length; j++) {
									if(optArr[j].value==formData[i]){
										optArr[j].selected=true;
										document.getElementById(i).disabled="disabled";
									}
								}
							}else{
								document.getElementById(i).value=formData[i];
							}
						}
					}
				}});
				document.getElementById("saveBtn").onclick=_editSaveData;
			}
		});
	};
	
	//==新增保存节点==//
	var _addSaveData = function(){
		var saveUrl="/sysrole/save_role.wmctl";
		_saveData(saveUrl);
	};
	//==编辑保存节点==//
	var _editSaveData = function(){
		var saveUrl="/sysrole/edit_save.wmctl";
		_saveData(saveUrl);
	};
	
	var _saveData = function(urlType) {
		var saveUrl =basePath + (urlType?urlType:"/menu/edit_save.wmctl");
		var formData = $("#IForm").serialize();
		AjaxRequest.asyncAjaxPost(saveUrl, formData, function(result) {
			// 释放按钮
			if (result.success == "true") {
				_reLoadData(_param);
				if(_theAddRoleWindow) _theAddRoleWindow.close();
				if(_theEditRoleWindow) _theEditRoleWindow.close();
			} else {
				alert(result.msg);
			}
		});
	};

	var _batDelBtnListener = function() {
		$('#batDelRole').click(function() {
			var roleIds = "";
			try {
				var myDatatable = $('table.datatable').data('zui.datatable');
				var ids =myDatatable.checks.checks;
				roleIds = ids.join(",");
			} catch (e) {
			//	console.error(e);
			}
			if(roleIds){
				Dialog.delDialog(function() {
					_delData(roleIds);
				});
			}else{
				alert("请选择要操作的数据！");
				return false;
			}
			
		});
	};
	var _delRole = function(id){
		if(id){
			Dialog.delDialog(function() {
				_delData(id);
			});
		}else{
			alert("请选择要操作的数据！");
			return false;
		}
		
	};
	
	var _delData = function(ids) {
		var delData  = {
				roleId : ids
			};
		var delUrl = basePath + "/sysrole/del_role.wmctl";
		AjaxRequest.asyncAjaxPost(delUrl, delData, function(result) {
			if (result.success = "true") {
				Dialog.getDelDialog().close();
				_reLoadData(_param);
			} else {
				alert(result.msg);
			}
		});
	};

	var _resetForm= function(){
		//$('#').reset();
	};
	
	//==授权==//
	var _authorize = function(roleId,roleName){
		var _theAuthorizeWindow = Dialog.dialog({
			backdrop : 'static',
			toggle : 'modal',
			height : '700px',
			width : '80%',
			title : '【'+roleName+'】'+'授权',
			iframe :basePath + '/sysrole/authorize_list.wmctl?roleId='+roleId,
			moveable : true 
		});
		_theAuthorizeWindow.show();
	};
	//==添加权限==//
	var _authority = function(roleId,roleName){
		var _theAuthorityWindow = Dialog.dialog({
			backdrop : 'static',
			toggle : 'modal',
			height : '700px',
			width : '80%',
			title : '【'+roleName+'】'+'权限',
			iframe :basePath + '/sysrole/authority_menu.wmctl?roleId='+roleId,
			moveable : true 
		});
		_theAuthorityWindow.show();
	};
	
	
	var _verification = function(){
		 
	};
	return {
		init : function() {
			//此处可以优化为 只传内部方法var _param = PageTool.init(_searchData,_cols, _url);
			_param = PageTool.init(RoleList);
			_batDelBtnListener();
			_searchData(_cols, _url);
		},
		searchData : function() {
			_searchData(_cols, _url);
		},
		reLoadData : function(param) {
			if(typeof param == "number"){//点击搜索
				_param.formParam =  $("#searchForm").serialize();
				_reLoadData(_param);
			}else if(typeof param == "object"){//点击分页
				//点击分页时注入查询条件
				param.formParam =  _param.formParam;
				var pf = param.formParam;
				if(pf!=$("#searchForm").serialize() ){
					if(pf){
//						表单填充
//						var arr =  $("#searchForm").serializeArray();
//						for (var i = 0; i < arr.length; i++) {
//							 arr[i].name;
//							 arr[i].value;
//						}
					}else{
						$('#searchForm')[0].reset();
					}
				} 
				_reLoadData(param);
			}else{
				 return false;
			}
		},
		addRoleWindow : function() {
			_addRoleWindow();
		},
		saveData : function() {
			_saveData();
		},
		editRoleWindow : function(id){
			_editRoleWindow(id);
		},
		delRole : function(id) {
			_delRole(id);
		},
		authorize:function(roleId,roleName){
			_authorize(roleId,roleName);
		},
		authority :function(roleId,roleName){
			_authority(roleId,roleName);
		}
		
	}
}();