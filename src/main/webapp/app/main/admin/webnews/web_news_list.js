/**
 * 用户管理
 * @author:WangMingM
 * @date:2017.11
 */
$(function() {
	WebNewsList.init();
});

var WebNewsList = function() {
	'use strict';
	var _cols = [ {
		width : 80,
		colName : 'title',
		text : '标题',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 80,
		colName : 'summary',
		text : '摘要',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 80,
		colName : 'author',
		text : '作者',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, {
		width : 80,
		colName : 'status',
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
		text : '新增时间',
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
			var html='<button name ="edituser" class="btn btn-mini"  onclick="javascript:WebNewsList.editUserWindow(\''+i+'\')"><i class="icon icon-edit"></i>编辑</button>'
					+'<button name ="deluser" class="btn btn-mini" onclick="javascript:WebNewsList.delUser(\''+i+'\')"><i class="icon icon-trash" style="color:red">删除</i></button>';
			return html;
		} , function uAccount (v){
			if(v=='admin'){
				return '';
			} 
		}]
	} ];
	
	var _url = basePath + "/webNews/webnews_list_data.wmctl";
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
		var addHmtl = UserAdd.getAddUserHtml();
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

	var _saveData = function() {
		var saveUrl = basePath + "/sysuser/save_user.wmctl";
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
			_param = PageTool.init(WebNewsList);
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
		saveData : function() {
			_saveData();
		},
		editUserWindow : function(id){
			_editUserWindow(id);
		},
		delUser : function(id) {
			_delUser(id);
		}
	}
}();