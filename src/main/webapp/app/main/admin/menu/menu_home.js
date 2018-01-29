/**
 * 导航树管理
 * 
 * @author:WangMingM
 * @date:2017.11.17
 */
$(function() {
	MenuHome.init();
});
var MenuHome = function() {
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
				flex : false,// 平行移动
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
			},
			{
				width : 80,
				colName : '_operation',
				text : '操作',
				type : 'string',
				flex : false,
				colClass : 'text-center',
				formatter : [ function uType(v, i) {
					var html =
					// '<button name ="deluser" class="btn btn-mini"
					// onclick="javascript:UserList.delUser(\''
					// + i
					// + '\')"><i class="icon icon-trash"
					// style="color:red">删除</i></button>'
					// +
					'<button name ="edituser" class="btn btn-mini"  onclick="javascript:MenuHome.editMenuWindow(\''
							+ i
							+ '\')"><i class="icon icon-edit"></i>编辑</button>';
					return html;

				} ]
			} ];

	var _url = basePath + "/menu/menu_tree.wmctl";
	var _param = null;
	var _data = null;

	// ==树容器ID==//
	var _zTreeId = "show_tree";
	// ==导航树对象==//
	var _treeObj = null;
	// ==编辑或新增节点时所选择的节点==//
	var _theSelectNode = null;
	// =========================定义方法===============================//

	var _theAddMenuWindow = null;
	// ==新增节点窗口==//
	var _addMenuWindow = function() {
		var addHmtl = MenuAdd.getAddMenuHtml();
		_theAddMenuWindow = Dialog.dialog({
			backdrop : 'static',
			toggle : 'modal',
			height : '450px',
			width : '700px',
			title : '添加',
			moveable : true,
			// custom : document.getElementById("ediMenuHtml")
			custom : function() {
				return addHmtl;
			}
		});
		_theAddMenuWindow.show();
		document.getElementById("saveBtn").onclick = _addSaveData;
	};

	var _theEditMenuWindow = null;
	// ==编辑节点窗口==//
	var _editMenuWindow = function(id) {
		var addHmtl = MenuAdd.getAddMenuHtml();
		var ediUrl = basePath + '/menu/edit_menu.wmctl';
		var data = {
			id : id
		};
		AjaxRequest
				.asyncAjaxPost(
						ediUrl,
						data,
						function(result) {
							if (result.success == "true") {
								_theEditMenuWindow = Dialog.dialog({
									backdrop : 'static',
									toggle : 'modal',
									height : '450px',
									width : '700px',
									title : '编辑',
									// remote :basePath +
									// '/menu/edit_menu.wmctl?id='+id,
									moveable : true,
									// custom
									// :document.getElementById("ediMenuHtml")
									custom : function() {
										return addHmtl;
									}
								});
								var formData = result.data;
								// ==填充数据并展示编辑窗口==//
								_theEditMenuWindow
								.show({
									onShow : function() {
										for ( var i in formData) {
											if (document.getElementById(i)) {
												if ("nodeType" == i) {
													document.getElementById(i).options[formData[i] + 1].selected = true;
													document.getElementById(i).disabled = "disabled";
												} else {
													document.getElementById(i).value = formData[i];
												}
											}
										}
									}
								});
								document.getElementById("saveBtn").onclick = _editSaveData;
							}
						});
	};
	// ==新增节点（实现过程非常依赖数据库初始化数据）==//
	var _addNode = function() {
		var nodes = _treeObj.getSelectedNodes();
		// ==不选节点添加一级菜单==//
		if (nodes.length == 0) {
			_addMenuWindow();
			$("#menuParent").val("ONE-LEVEL");
			$("#nodeType").find("option[value = '1']").attr("selected",
					"selected");
			$("#nodeType").attr("disabled", "disabled");
		} else {
			// ==选择“首页”节点添加一级节点==/
			if ("ONE-LEVEL-HOME-PAGE" == nodes[0].id) {
				_addMenuWindow();
				$("#menuParent").val("ONE-LEVEL");
				$("#nodeType").find("option[value = '1']").attr("selected",
						"selected");
				$("#nodeType").attr("disabled", "disabled");
			} else {
				if (nodes[0].data.nodeType == 1) {
					_addMenuWindow();
					$("#nodeType").removeAttr("disabled");
					$("#menuParent").val(nodes[0].id);
					$("#menuSort").val(
							nodes[0].children ? (nodes[0].children.length + 1)
									: 1);
				} else {
					alert("叶子节点下无法添加子节点!");
				}
			}
		}
	};
	// ==新增保存节点==//
	var _addSaveData = function() {
		if(_saveVeri()){
			return;
		}
		$("#nodeType").removeAttr("disabled");
		var saveUrl = basePath + "/menu/save_menu.wmctl";
		var formData = $("#IForm").serialize();
		AjaxRequest.asyncAjaxPost(saveUrl, formData, function(result) {
			// 释放按钮
			var data = result.data;
			if (result.success == "true") {
				// 此处可改进为只刷新部分节点（暂不实现17.11.22注）
				_getTree();
				if (_theSelectNode) {
					if ("ONE-LEVEL-HOME-PAGE" == _theSelectNode.id) {
						var newNodes = _treeObj.getNodesByParam("id", data.id,
								null);
						_theSelectNode = newNodes[0];
						_treeObj.selectNode(_theSelectNode, false, false);
						var sdata = _theSelectNode.data;
						_showTable([ sdata ], null);
					} else {
						if (_theSelectNode.data.nodeType == 1) {
							_theSelectNode.data.children.push(data);
							_treeObj.selectNode(_theSelectNode, false, false);
							var sdata = _theSelectNode.data;
							_showTable([ sdata ], sdata.children);
						} else {
							alert("叶子节点下无法添加子节点!");
						}
					}
				} else {
					var newNodes = _treeObj
							.getNodesByParam("id", data.id, null);
					_theSelectNode = newNodes[0];
					_treeObj.selectNode(_theSelectNode, false, false);
					var sdata = _theSelectNode.data;
					_showTable([ sdata ], sdata.children);
				}
				if (_theAddMenuWindow)
					_theAddMenuWindow.close();
			} else {
				alert(result.msg);
			}
		});
	};
	// ==编辑保存节点==//
	var _editSaveData = function() {
		if(_saveVeri()){
			return;
		}
		$("#nodeType").removeAttr("disabled");
		var saveUrl = basePath + "/menu/edit_save.wmctl";
		var formData = $("#IForm").serialize();
		AjaxRequest.asyncAjaxPost(saveUrl, formData, function(result) {
			// 释放按钮
			var data = result.data;
			if (result.success == "true") {
				// 此处可改进为只刷新部分节点（暂不实现17.11.22注）
				_getTree();
				_theSelectNode = _treeObj
						.getNodesByParam("id", _theSelectNode.id, null)[0];
				_treeObj.selectNode(_theSelectNode, false, false);
				if (_theSelectNode && _theSelectNode.data.nodeType == 0) {
					if (data.id == _theSelectNode.id) {
						var sdata = _theSelectNode.data;
						var pareNode = _theSelectNode.getParentNode();
						var arr = [ sdata ];
						_showTable([ pareNode.data ], arr);
					} else {
						var pareNode = _theSelectNode.getParentNode();
						var sdata = pareNode.data;
						_showTable([ sdata ], data.children);
					}
					// _treeObj.setting.callback.onClick(event,_zTreeId,
					// _theSelectNode);
				} else if (_theSelectNode && _theSelectNode.data.nodeType == 1) {
					if (data.id == _theSelectNode.id) {
						var sdata = _theSelectNode.data;
						_showTable([ sdata ], null);
					} else {
						var editNodes = _treeObj.getNodesByParam("id", data.id, null);
						// _treeObj.setting.callback.onClick(event,_zTreeId,editNodes[0]);
						var pareNode = editNodes[0].getParentNode();
						var arr = pareNode.data.children;
						_showTable([ pareNode.data ], arr);
					}
				}
				if (_theEditMenuWindow)
					_theEditMenuWindow.close();
			} else {
				alert(result.msg);
			}
		});
	};
	var _saveVeri = function(){
		var name = $.trim($("#menuName").val());
		console.log(name);
		if(name==""||name==undefined){
			alert("请输入名称");
			return true;
		}
		var nodeType =  $("#nodeType").val();
		if(nodeType==""||nodeType==undefined){
			alert("请选者类型");
			return true;
		}
		return false;
	};
	// ==获得导航树数据==//
	var _getTree = function() {
		var url = basePath + "/menu/menu_tree.wmctl";
		AjaxRequest.asyncAjax(url, null, function(result) {
			if (result.success == "true") {
				var json = result.data;
				_showTree(json);
			} else {
				alert("导航树获取失败!");
			}
		});
	};
	// ==根据航树数据初始化树对象==//
	var _showTree = function(t) {
		var setting = {
			async : {
				enable : true,// 开启异步加载
				url : basePath + "/menu/menu_tree.wmctl",
				autoParam : [ "id" ],
				type : "get"// 默认post
			// otherParam: ["id", "1", "name", "test"]// 其他参数：将提交参数
			// id=1&name=test
			},
			callback : {
				onClick : _zTreeOnClick,
				onNodeCreated : _zTreeOnNodeCreated
			},
			view : {
				selectedMulti : false
			// 不支持多选节点
			}
		};
		_treeObj = $.fn.zTree.init($("#" + _zTreeId), setting, t);
	};
	// ==表格展示节点信息==//
	var _showTable = function(parent, record) {
		if (parent) {
			var rowSet = {
				cssClass : "danger"
			};
			var par = DataModel.buildDataModel(_cols, parent, rowSet);
			$("#parentNodeTable").datatable("load", par);
		}
		if (record) {
			var rowSet = {
				cssClass : "success"
			};
			_data = DataModel.buildDataModel(_cols, record, rowSet);
			$("#childNodeTable").datatable("load", _data);
		}
	};
	// ==初始化导航树的回调函数（监听导航树点击事件）==//
	var _zTreeOnClick = function(event, treeId, treeNode) {
		console.log(treeNode);
		var data = treeNode.data;
		_theSelectNode = treeNode;
		if (data.nodeType == 0) {// 叶子节点
			var pareNode = treeNode.getParentNode();
			var arr = [ data ];
			_showTable([ pareNode.data ], arr);
		} else {
			_showTable([ data ], data.children);
		}
	};
	// ==初始化导航树的回调函数（用来处理“首页”节点的特殊性）==//
	var _zTreeOnNodeCreated = function(event, treeId, treeNode) {
		if ("ONE-LEVEL-HOME-PAGE" == treeNode.id) {
			treeNode.icon = basePath + "/app/images/ztree_home_page.png";
			$.fn.zTree.getZTreeObj(treeId).updateNode(treeNode);
		}
	};
	// ==模糊搜索导航树节点==//
	var _searchNodes = function() {
		// var aa= _treeObj.getNodesByParam("name", "务管理", null);//精确查询
		var value = $.trim($("#searchNodes").val());
		if (value) {
			_getTree();
			var nodeArr = _treeObj.getNodesByParamFuzzy("name", value, null);
			if (nodeArr.length > 0) {
				for (var i = 0; i < nodeArr.length; i++) {
					_treeObj.setting.view.fontCss["color"] = "red";
					_treeObj.updateNode(nodeArr[i]);
				}
				_treeObj.selectNode(nodeArr[0], false, false);
				_treeObj.setting.callback.onClick(event, _zTreeId, nodeArr[0]);
				_treeObj.expandAll(true);
			}
		}
		// else{
		// _getTree();
		// }
	};
	// ==删除节点==//
	var _delNodes = function() {
		var nodes = _treeObj.getSelectedNodes();
		if (nodes.length <= 0) {
			alert("请选择要删除的节点！");
			return false;
		}
		// ==当前节点==//
		var theSelectNode = nodes[0];
		// ==父节点==//
		var parenNode = theSelectNode.getParentNode();
		// ==上个节点==//
		var preNode = theSelectNode.getPreNode();
		// ==下个节点==//
		var nextNode = theSelectNode.getNextNode();
		var theChildren = parenNode ? parenNode.data.children : null;
		if ("ONE-LEVEL-HOME-PAGE" == theSelectNode.id) {
			alert("首页无法删除！");
		} else {
			Dialog.delProDialog("该节点和其所有子节点都将删除，确定删除吗？", function() {
				var delUrl = basePath + "/menu/del_menu.wmctl";
				var delData = {
					menuId : theSelectNode.data.id,
					nodeType : theSelectNode.data.nodeType
				};
				AjaxRequest.asyncAjaxPost(delUrl, delData, function(result) {
					if (result.success == "true") {
						// alert(result.msg);
						_treeObj.removeNode(theSelectNode);
						if (nextNode) {
							for (var i = 0; theChildren
									&& i < theChildren.length; i++) {
								if (theChildren[i].id == theSelectNode.id) {
									theChildren.splice(i, 1);
								}
							}
							_treeObj.selectNode(nextNode, false, false);
							_treeObj.setting.callback.onClick(event, _zTreeId,
									nextNode);
						} else if (preNode) {
							for (var j = 0; theChildren
									&& j < theChildren.length; j++) {
								if (theChildren[j].id == theSelectNode.id) {
									theChildren.splice(j, 1);
								}
							}
							_treeObj.selectNode(preNode, false, false);
							_treeObj.setting.callback.onClick(event, _zTreeId,
									preNode);
						} else if (parenNode) {
							parenNode.data.children = [];
							_treeObj.selectNode(parenNode, false, false);
							_treeObj.setting.callback.onClick(event, _zTreeId,
									parenNode);// 清空子节点
						}
					} else {
						alert(result.msg);
					}
				});
				Dialog.getDelDialog().close();
			});
		}
	};

	return {
		init : function() {
			_getTree();
			// _treeObj = $.fn.zTree.getZTreeObj(_zTreeId);
		},
		addNode : function() {
			_addNode();
		},
		addMenuWindow : function() {
			_addMenuWindow();
		},
		editMenuWindow : function(id) {
			_editMenuWindow(id);
		},
		// addSaveData : function(){
		// _addSaveData();
		// },
		// editSaveData : function(edit){
		// _editSaveData(edit);
		// },
		reFresh : function() {
			_theSelectNode=null;
			_getTree();
		},
		expandAll : function(fal) {
			_treeObj.expandAll(fal);
		},
		searchNodes : function() {
			_searchNodes();
		},
		delNodes : function() {
			_delNodes();
		}
	}
}();