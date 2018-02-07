/**
 * 导航树管理
 * @author:WangMingM
 * @date:2017.11.17
 */
$(function() {
	AuthorityMenu.init();
});
var AuthorityMenu = function() {
	'use strict';
	var _cols = [
			{
				width : 150,
				colName : 'menuName',
				text : '名称',
				type : 'string',
				flex : false,
				colClass : 'text-center'
			},
			{
				width : 120,
				colName : 'menuIcon',
				text : '图标',
				type : 'string',
				flex : false,
				colClass : 'text-center'
			},
			{
				width : 'auto',
				colName : 'menuUrl',
				text : '地址',
				type : 'string',
				flex : false,//平行移动
				colClass : 'text-center'
			},
			{
				width : 120,
				colName : 'isAvailable',
				text : '是否可用',
				type : 'string',
				flex : false,
				colClass : 'text-center',
				formatter : [ function(v) {
					if (v == 0)
						return "可用";
					else if (v == 1)
						return "废弃";
					else
						return "--";
				} ]
			},
			{
				width : 120,
				colName : 'nodeType',
				text : '节点类型',
				type : 'string',
				flex : false,
				colClass : 'text-center',
				formatter : [ function(v) {
					if (v == 0)
						return "叶子节点";
					else if (v == 1)
						return "父/分支节点";
					else
						return "--";
				} ]
			}];

	var _param = null;
	var _data = null;
	var _roleId=null;
	var _initSelectNode=null;
	//==选中节点IDs==//
	var _selectNode=null;
	//==取消选中节点IDs==//
	var _removeNode=null;
	//==取消选中节点类型==//
	var _rmNodeType=null;
	//==树容器ID==//
	var _zTreeId="show_tree";
	//==导航树对象==//
	var _treeObj = null;
	//==编辑或新增节点时所选择的节点==//
	var _theSelectNode = null;
	// =========================定义方法===============================//
	
	//==获得导航树数据==//
	var _getTree = function() {
		var url = basePath + "/menu/menu_tree.wmctl";
		var data = {
				roleId :_roleId
		};
		AjaxRequest.asyncAjax(url, data, function(result) {
			if (result.success == "true") {
				var json = result.data;
				_showTree(json);
			} else {
				alert("导航树获取失败!");
			}
		});
	};
	//==根据航树数据初始化树对象==//
	var _showTree = function(t) {
		var setting = {
			async : {
				enable : true,//开启异步加载
				url : basePath + "/menu/menu_tree.wmctl",
				autoParam : [ "id" ],
				type: "get",//默认post
				otherParam: ["roleId", _roleId]// 其他参数：将提交参数 id=1&name=test
			},
			check: {
				enable: true,
				chkStyle: "checkbox",
				chkboxType: { "Y": "ps", "N": "ps" }
			},
			callback: {
				onClick: _zTreeOnClick,
				onNodeCreated: _zTreeOnNodeCreated 
			} ,
			view: {
			    selectedMulti: false//不支持多选节点
			}
		};
		  _treeObj = $.fn.zTree.init($("#"+_zTreeId), setting, t);
	};
	//==表格展示节点信息==//
	var _showTable = function(parent,record){
		if(parent){
			var rowSet = {
					cssClass: "danger"
			};
			var par = DataModel.buildDataModel(_cols, parent,rowSet);
			$("#parentNodeTable").datatable("load", par);
		}
		if(record){
			var rowSet = {
					cssClass: "success"
			};
			_data = DataModel.buildDataModel(_cols, record,rowSet);
			$("#childNodeTable").datatable("load", _data);
		}
	};
	//==初始化导航树的回调函数（监听导航树点击事件）==//
	var _zTreeOnClick = function(event, treeId, treeNode){
		console.log(treeNode);
		var menuId = treeNode.id;
		_getAction(menuId);
	};
	//==初始化导航树的回调函数（用来处理“首页”节点的特殊性）==//
	var _zTreeOnNodeCreated = function(event, treeId, treeNode){
		if("ONE-LEVEL-HOME-PAGE"==treeNode.id){
			treeNode.icon=basePath+"/app/images/ztree_home_page.png";
			$.fn.zTree.getZTreeObj(treeId).updateNode(treeNode);
		}
	};
	//==模糊搜索导航树节点==//
	var _searchNodes = function(){
		//var aa= _treeObj.getNodesByParam("name", "务管理", null);//精确查询
		var value = $.trim($("#searchNodes").val());
		if(value){
			_getTree();
			var nodeArr= _treeObj.getNodesByParamFuzzy("name", value, null);
			if(nodeArr.length>0){
				for (var i = 0; i < nodeArr.length; i++) {
					_treeObj.setting.view.fontCss["color"] = "red";
					_treeObj.updateNode(nodeArr[i]);
				}
				_treeObj.setting.callback.onClick(event, _zTreeId, nodeArr[0]);
				_treeObj.expandAll(true);
			}
		}
//		else{
//			_getTree();
//		}
	};
    var _saveRoleMenu = function(){
    	_getSaveParam();
    	var url= basePath + "/sysrole/authority_save.wmctl";
    	var data ={
    			roleId : _roleId,
    			selectNode : _selectNode,
    			removeNode : _removeNode,
    			rmNodeType : _rmNodeType
    	};
		AjaxRequest.asyncAjaxPost(url, data, function(result) {
			if (result.success == "true") {
				alert(result.msg);
			}else{
				alert(result.msg);
			}
		});
    };

    var _getSaveParam= function(){
    	var selects=new Array();
    	var removes=new Array();
    	var nodeType=new Array();
    	var nodes=_treeObj.getChangeCheckedNodes();
    	for (var i = 0; i < nodes.length; i++) {
    		var node = nodes[i];
    		if(node.checked===true){
        		selects.push(node.data.id);
        	}else if(node.checked===false){
        		removes.push(node.data.id);
        		nodeType.push(node.data.nodeType)
        	}
		}
    	_selectNode = selects.join(",");
    	_removeNode = removes.join(",");
    	_rmNodeType = nodeType.join(",");
    };
	
    var _getAction = function(menuId){
    	
    	var url= basePath + "/sysAction/get_auth_act.wmctl";
    	var data ={
    			roleId : _roleId,
    			menuId : menuId
    	};
		AjaxRequest.asyncAjaxPost(url, data, function(result) {
			if (result.success == "true") {
				$("#act_wx").empty();
				$("#act_yx").empty();
				
				var actArr = result.data;
				var wx="";
				var yx="";
				for (var i = 0; i < actArr.length; i++) {
					var yw = actArr[i];
					if(yw.auttyMark==0){
						wx+='<button class="btn btn-sm btn-warning " type="button" data-id="'+yw.id+'" onclick="AuthorityMenu.xclick(this)">'+yw.actName+'</button>';
					}else if(yw.auttyMark>0){
						yx+='<button class="btn btn-sm btn-warning " type="button" data-id="'+yw.id+'" onclick="AuthorityMenu.xclick(this)">'+yw.actName+'</button>';
					}
				}
				$("#act_wx").append(wx);
				$("#act_yx").append(yx);
			}else{
				alert(result.msg);
			}
		});
    	
    	
    };
    var _xclick = function(o){
    	var ele = $(o);
    	var war = ele.hasClass('btn-warning');
    	if(war==true){
    		ele.removeClass("btn-warning");
    		ele.addClass("btn-success");
    	}else{
    		ele.removeClass("btn-success");
    		ele.addClass("btn-warning");
    	}
    	
//    	AjaxRequest.codeEff(10000,function(oo){
//    	 
//    	var s =	oo.hasClass('btn-warning');
//    	
//    	if(s==true){
//    		var ddd="ee";
//    	}
//    	},$(o));
    	
//    	   1、$(selector).is(‘.classname’);  
//    	   2、$(selector).hasClass(‘classname’)；
    	
//    	var btns = $("#act_wx .btn-warning");
//    	
//    	$.each(btns,function(i,v){
//    		
//    		console.log(v);
//    	});
//    	
//    	console.log(btns);
    	
    };
    
    var _add = function(){
    	var btns = $("#act_wx .btn-success");
    	var yxDiv = $("#act_yx");
    	console.log(btns);
    	var arr =[];
    	if(btns.length>0){
        	$.each(btns,function(i,v){
        		var vv = $(v);
        		arr.push(vv.data("id"));
        		v.remove();
        		vv.removeClass("btn-success");
        		vv.addClass("btn-warning");
        		yxDiv.append(vv);
    	    });
        	var url= basePath + "/sysAction/add_act.wmctl";
        	var data={actIds:arr,
        			roleId:_roleId};
        	
        	AjaxRequest.asyncAjaxPost(url, data, function(result) {
        		
        		console.log(result);
        		
        	});
    	}
    };
    var _remove = function(){
    	var btns = $("#act_yx .btn-success");
    	var yxDiv = $("#act_wx");
    	var arr =[];
    	if(btns.length>0){
        	$.each(btns,function(i,v){
        		var vv=$(v);
        		arr.push(vv.data("id"));
        		v.remove();
        		vv.removeClass("btn-success");
        		vv.addClass("btn-warning");
        		yxDiv.append(vv);
    	});
        	var url= basePath + "/sysAction/remove_act.wmctl";
        	var data={actIds:arr,
        			roleId:_roleId};
        	
        	AjaxRequest.asyncAjaxPost(url, data, function(result) {
        		
        		console.log(result);
        		
        	});
    	}
    };
	return {
		init : function() {
			///$('#dashboard').dashboard({draggable: true});
			
			_roleId = $("#roleId").val();
			_getTree();
		},
		reFresh : function(){
			_getTree();
		},
		expandAll : function(fal){
			_treeObj.expandAll(fal);
		},
		searchNodes : function(){
			_searchNodes();
		},
		saveRoleMenu :function(){
			_saveRoleMenu();
		},
		xclick:function(o){
			_xclick(o);
		},
		add : function(){
			_add();
		},
		remove : function(){
			_remove();
		}
	}
}();