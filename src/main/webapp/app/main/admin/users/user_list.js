/**
 * 用户管理
 * @author:WangMingM
 * @date:2017.11
 */
$(function() {
	UserList.init();
});

var UserList = function() {
	'use strict';
	var _cols = [ {
		width : 80,
		colName : 'uAccount',
		text : '账号',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 80,
		colName : 'uName',
		text : '姓名',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 80,
		colName : 'uEmail',
		text : '邮箱',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 80,
		colName : 'uTel',
		text : '电话',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	},{
		width : 80,
		colName : 'roleName',
		text : '角色',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 80,
		colName : 'uType',
		text : '类型',
		type : 'string',
		flex : false,
		colClass : 'text-center',
		formatter:[function(v){
			if(v=="A") return "内网";
			else if(v=="B") return "外网";
			else return "--";
		}]
	}, {
		width : 80,
		colName : 'uStatus',
		text : '状态',
		type : 'string',
		flex : false,
		colClass : 'text-center',
		formatter:[function(v){
			if(v=="Y") return "正常";
			else if(v=="N") return "已禁用";
			else return "未激活";
		}]
	}, {
		width : 80,
		colName : 'createTime',
		text : '注册时间',
		type : 'date',
		flex : false,
		colClass : 'text-center',
		sort : 'down',
		formatter:[function (v,i){
			return moment(v).format("YYYY-MM-DD HH:mm");
		}]
	}, {
		width : 80,
		colName : '_operation',
		text : '操作',
		type : 'string',
		flex : false,
		colClass : 'text-center',
		formatter : [ function uType (v,i) {
			var btn=[
			         {name:'edituser',clazz:'btn btn-mini',onclick:'UserList.editUserWindow(\''+i+'\')',icon:'icon icon-edit',actName:'编辑'},
			         {name:'deluser',clazz:'btn btn-mini ',onclick:'UserList.delUser(\''+i+'\')',icon:'icon icon-trash',bstyle:'margin-left: 3px;',istyle:'color:red;',actName:'删除'}
			        ];
			return PermissionAct.build(btn,_menuId);
		} , function uAccount (v){
			if(v=='admin'){
				return '';
			} 
		}]
	} ];
	
	var _menuId=$(window.frameElement).data("menuid");
	var _url = basePath + "/sysuser/user_list_data.wmctl";
	var _param=null;
	var _data = null;
	// =========================定义方法===============================//
	var _searchData = function(cols, url) {
		AjaxRequest.asyncAjax(url, _param, function(result) {
			var record = result.data;
			_data = DataModel.buildDataModel(cols, record);
			$('table.datatable').datatable({
				checkable : true,
				checkByClickRow : false,
				fixCellHeight :false,
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

	var _theAddUserWindow = null;
	var _addUserWindow = function() {
		var addHmtl = UserAdd.addWin("saveSave");
		_theAddUserWindow = Dialog.dialog({
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
		_theAddUserWindow.show();
	};

	var _saveData = function(type) {
		var saveUrl = basePath + "/sysuser/"+type+".wmctl";
		var formData = $("#IForm").serialize();
		// var shuju = decodeURIComponent(data,true); //将数据解码
		AjaxRequest.asyncAjaxPost(saveUrl, formData, function(result) {
			// 释放按钮
			if (result.success == "true") {
				_reLoadData(_param);
				if(_theAddUserWindow) _theAddUserWindow.close();
				if(_theEditUserWindow) _theEditUserWindow.close();
			} else {
				alert(result.msg);
			}
		});
	};
	var _theEditUserWindow = null;
	var _editUserWindow = function(id) {
		_theEditUserWindow = Dialog.dialog({
			backdrop : 'static',
			toggle : 'modal',
			height : '400px',
			width : '700px',
			title : '编辑',
			remote :basePath + '/sysuser/edit_user.wmctl?id='+id,
			moveable : true 
		});
		_theEditUserWindow.show();
	};
	var _batDelBtnListener = function() {
		$('#batdeluser').click(function() {
			var userIds = "";
			try {
				var myDatatable = $('table.datatable').data('zui.datatable');
				var ids =myDatatable.checks.checks;
				 userIds = ids.join(",");
			} catch (e) {
			//	console.error(e);
			}
			if(userIds){
				Dialog.delDialog(function() {
					_delData(userIds);
				});
			}else{
				alert("请选择要操作的数据！");
				return false;
			}
			
		});
	};
	var _delUser = function(userId){
		if(userId){
			Dialog.delDialog(function() {
				_delData(userId);
			});
		}else{
			alert("请选择要操作的数据！");
			return false;
		}
		
	};
	
	var _delData = function(userIds) {
		var delData  = {
				userId : userIds
			};
		var delUrl = basePath + "/sysuser/del_user.wmctl";
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
	return {
		init : function() {
			//此处可以优化为 只传内部方法var _param = PageTool.init(_searchData,_cols, _url);
			_param = PageTool.init(UserList);
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
		addUserWindow : function() {
			_addUserWindow();
		},
		saveSave : function() {
			_saveData("add_save");
		},
		editSave : function() {
			_saveData("edit_save");
		},
		editUserWindow : function(id){
			_editUserWindow(id);
		},
		delUser : function(id) {
			_delUser(id);
		}
	}
}();