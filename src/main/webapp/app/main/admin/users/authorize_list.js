$(function() {

	AuthorizeList.init();
});
var AuthorizeList = function() {
	'use strict';
	var _cols = [
			{
				width : 80,
				colName : 'uAccount',
				text : '账号',
				type : 'string',
				flex : false,
				colClass : 'text-center'
			},
			{
				width : 80,
				colName : 'uName',
				text : '姓名',
				type : 'string',
				flex : false,
				colClass : 'text-center'
			},
			{
				width : 80,
				colName : 'uEmail',
				text : '邮箱',
				type : 'string',
				flex : false,
				colClass : 'text-center'
			},
			{
				width : 80,
				colName : 'uTel',
				text : '电话',
				type : 'string',
				flex : false,
				colClass : 'text-center'
			},
			{
				width : 80,
				colName : 'uType',
				text : '类型',
				type : 'string',
				flex : false,
				ignore : true,// 忽略该列
				colClass : 'text-center',
				formatter : [ function(v) {
					if (v == "A")
						return "管理员";
					else if (v == "B")
						return "普通用户";
					else
						return "--";
				} ]
			},
			{
				width : 80,
				colName : 'roleName',
				text : '角色',
				type : 'string',
				flex : false,
				colClass : 'text-center'
			},
			{
				width : 80,
				colName : 'uStatus',
				text : '状态',
				type : 'string',
				flex : false,
				colClass : 'text-center',
				formatter : [ function(v) {
					if (v == "Y")
						return "正常";
					else if (v == "N")
						return "已禁用";
					else
						return "未激活";
				} ]
			},
			{
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
			},
			{
				width : 80,
				colName : '_operation',
				text : '操作',
				type : 'string',
				flex : false,
				colClass : 'text-center',
				formatter : [
						function autzeMark(v, i) {
							if (v == 1) {
								return '<button name ="squser" class="btn btn-mini btn-warning" onclick="javascript:AuthorizeList.cancelAutze(\''
										+ i
										+ '\')" style="margin-right: 20px;"><i class="icon icon-reply-all">撤权</i></button>';
							} else if (v == 0) {
								return '<button name ="squser" class="btn btn-mini btn-success" onclick="javascript:AuthorizeList.authorize(\''
										+ i
										+ '\')" style="margin-right: 20px;"><i class="icon icon-hand-right">授权</i></button>';
							} else {
								return '--';
							}
						} ]
			} ];

	var _url = basePath + "/sysrole/authorize_user_list.wmctl";
	var _param = null;
	var _data = null;
	var _roleId = null;
	// =========================定义方法===============================//
	var _searchData = function(cols, url) {
		AjaxRequest.asyncAjax(url, _param, function(result) {
			var record = result.data;
			_data = DataModel.buildDataModel(cols, record);
			$('table.datatable').datatable({
				checkable : true,
				checkByClickRow : false,
				fixCellHeight : false,
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

	

	var _batAutzeBtnListener = function() {
		$('#batautzeuser').click(function() {
			var userIds = "";
			try {
				var myDatatable = $('table.datatable').data('zui.datatable');
				var ids = myDatatable.checks.checks;
				userIds = ids.join(",");
			} catch (e) {
				// console.error(e);
			}
			if (userIds) {
					_authorize(userIds);
			} else {
				alert("请选择要操作的数据！");
				return false;
			}

		});
	};

	var _authorize = function(userIds) {
		// 授权按钮做倒计时限制，防止连续操作
		var autzeData = {
			roleId : _roleId,
			userId : userIds
		};
		var autzeUrl = basePath + "/sysrole/authorize_user.wmctl";
		AjaxRequest.asyncAjaxPost(autzeUrl, autzeData, function(result) {
			if (result.success = "true") {
				_reLoadData(_param);
			} else {
				alert(result.msg);
			}
		});
	};
	var _batCanCelAutzeBtnListener = function() {
		$('#batcancelautze').click(function() {
			var userIds = "";
			try {
				var myDatatable = $('table.datatable').data('zui.datatable');
				var ids = myDatatable.checks.checks;
				userIds = ids.join(",");
			} catch (e) {
				// console.error(e);
			}
			if (userIds) {
					_cancelAutze(userIds);
			} else {
				alert("请选择要操作的数据！");
				return false;
			}
		});
	};
	var _cancelAutze = function(userIds){
		// 授权按钮做倒计时限制，防止连续操作
		var autzeData = {
			roleId : _roleId,
			userId : userIds
		};
		var zutzeUrl = basePath + "/sysrole/cancel_authorize.wmctl";
		AjaxRequest.asyncAjaxPost(zutzeUrl, autzeData, function(result) {
			if (result.success = "true") {
				_reLoadData(_param);
			} else {
				alert(result.msg);
			}
		});
	};
	
	var _resetForm = function() {
		// $('#').reset();
	};

	return {
		init : function() {
			_roleId = $("#roleId").val();
			// 此处可以优化为 只传内部方法var _param = PageTool.init(_searchData,_cols,
			// _url);
			_param = PageTool.init(AuthorizeList);
			_param.roleId = _roleId;
			_batAutzeBtnListener();
			_batCanCelAutzeBtnListener();
			_searchData(_cols, _url);
		},
		searchData : function() {
			_searchData(_cols, _url);
		},
		reLoadData : function(param) {
			if (typeof param == "number") {// 点击搜索
				_param.formParam = $("#searchForm").serialize();
				_reLoadData(_param);
			} else if (typeof param == "object") {// 点击分页
				// 点击分页时注入查询条件
				param.formParam = _param.formParam;
				param.roleId = _param.roleId;
				var pf = param.formParam;
				if (pf != $("#searchForm").serialize()) {
					if (pf) {
						// 表单填充
						// var arr = $("#searchForm").serializeArray();
						// for (var i = 0; i < arr.length; i++) {
						// arr[i].name;
						// arr[i].value;
						// }
					} else {
						$('#searchForm')[0].reset();
					}
				}
				_reLoadData(param);
			} else {
				return false;
			}
		},
		authorize : function(userId) {
			_authorize(userId);
		},
		cancelAutze : function(userId){
			_cancelAutze(userId);
		}
	}
}();