/**
 * 评论管理
 * @author:WangMingM
 * @date:2018.1.04
 */
$(function() {
	VideoComtList.init();
});

var VideoComtList = function() {
	'use strict';
	var _cols = [ {
		width : 30,
		colName : 'topicId',
		text : '视频id',
		type : 'string',
		flex : false,
		sort : false,
		colClass : 'text-center'
	}, {
		width : 600,
		colName : 'content',
		text : '评论',
		type : 'string',
		flex : true,
		sort : false,
		colClass : 'text-center'
	}, {
		width : 100,
		colName : 'comtUname',
		text : '评论人',
		type : 'string',
		flex : true,
		sort : false,
		colClass : 'text-center'
	}, {
		width : 30,
		colName : 'likeCount',
		text : '点赞数',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	},{
		width : 30,
		colName : 'dislikeCount',
		text : '点踩数',
		type : 'string',
		flex : false,
		colClass : 'text-center'
	}, 
//	{
//		width : 80,
//		colName : 'dislikeCount',
//		text : '被举报数',
//		type : 'string',
//		flex : false,
//		colClass : 'text-center'
//	},
	{
		width : 30,
		colName : 'vcType',
		text : '类型',
		type : 'string',
		flex : false,
		sort : false,
		colClass : 'text-center',
		formatter:[function(v){
			if(v=="PL") return "评论";
			else if(v=="HF") return "回复";
			else return "--";
		}]
	}, {
		width : 70,
		colName : 'createTime',
		text : '评论时间',
		type : 'date',
		flex : false,
		colClass : 'text-center',
		sort : 'down',
		formatter:[function (v,i){
			return moment(v).format("YYYY-MM-DD HH:mm");
		}]
	}, {
		width : 70,
		colName : '_operation',
		text : '操作',
		type : 'string',
		flex : false,
		sort : false,
		colClass : 'text-center',
		formatter : [ function uType (v,i) {
			var html='<button name ="deluser" class="btn btn-mini" onclick="javascript:VideoComtList.delVideoComt(\''+i+'\')"><i class="icon icon-trash" style="color:red">删除</i></button>';
			return html;
		}]
	} ];
	
	var _url = basePath + "/videoComment/vc_list_data.wmctl";
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
				fixCellHeight :true,
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
	var _batDelBtnListener = function() {
		$('#batdel').click(function() {
			var comtIds = "";
			try {
				var myDatatable = $('table.datatable').data('zui.datatable');
				var ids =myDatatable.checks.checks;
				comtIds = ids.join(",");
			} catch (e) {
			//	console.error(e);
			}
			if(comtIds){
				Dialog.delDialog(function() {
					_delData(comtIds);
				});
			}else{
				alert("请选择要操作的数据！");
				return false;
			}
			
		});
	};
	var _delVideoComt = function(cId){
		if(cId){
			Dialog.delDialog(function() {
				_delData(cId);
			});
		}else{
			alert("请选择要操作的数据！");
			return false;
		}
		
	};
	
	var _delData = function(cIds) {
		var delData  = {
				cId : cIds
			};
		var delUrl = basePath + "/videoComment/del_comt.wmctl";
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
			_param = PageTool.init(VideoComtList);
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
		delVideoComt : function(id) {
			_delVideoComt(id);
		}
	}
}();